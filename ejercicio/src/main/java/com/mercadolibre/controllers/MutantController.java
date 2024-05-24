package com.mercadolibre.controllers;

import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.mercadolibre.dtos.JsonReceive;
import com.mercadolibre.dtos.Request;
import com.mercadolibre.mongo.ItemRepository;
import com.mercadolibre.services.CacheService;
import com.mercadolibre.services.MutantService;

import jakarta.annotation.PostConstruct;

@RestController
public class MutantController {

    Logger log = LogManager.getLogger(MutantController.class);

	@Autowired
	private MutantService service;

	@Autowired
    ItemRepository ejercicioItemRepo;

	@Autowired
	CacheService cache;

		@PostMapping("/mutant")
		public ResponseEntity<Boolean> isMutant(@RequestBody JsonReceive json) 
		throws  ResponseStatusException {

			checkRequest(json);

			if ( cache.stayInCache(json.getDna()) ) {
				return response(cache.get( json.getDna() ));
			}

			List<Request> resultsInMongo = ejercicioItemRepo.findByDna( json.getDna() );
			log.info("se consultara en mongo resultados");
			if ( !resultsInMongo.isEmpty() && resultsInMongo.get(0).getResult() ){
				return response(resultsInMongo.get(0).getResult());
			}
 
			boolean result = service.isMutant(json.getDna());

			cache.save(json.getDna(), result);
			log.info("se guardara el dato en MONGO");
			ejercicioItemRepo.save(new Request(json.getDna(), result));
			
			if( result ) {
				cache.getCountMutantDna().incrementAndGet();
				log.info("se actualizo la CACHE, el contador de mutantes");
				return response(result);
			} else {
				cache.getCountHumanDna().incrementAndGet();
				log.info("se actualizo la cache, el contador de humanos");
			}

			return new ResponseEntity<>(false, HttpStatus.FORBIDDEN);
		}

		private ResponseEntity<Boolean> response(boolean result) {
			if( result ) {
				return new ResponseEntity<>(Boolean.valueOf(result), HttpStatus.OK);
			}
			return new ResponseEntity<>(result, HttpStatus.FORBIDDEN);
		}

		private void checkRequest(JsonReceive json) throws ResponseStatusException {

			if (json.getLargo() != 6 ){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad size of dna entity, not is 6");
			}

			for(int r = 0; r < json.getLargo() ; r++) {
				if ( json.getAncho(r) != 6) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad value of dna, not is 6");
				}
			}

			HashSet<Character> check = new HashSet<Character>();
			check.add('G');
			check.add('A');
			check.add('T');
			check.add('C');
			
			for(int i=0; i < json.getLargo(); i++) {
				HashSet<Character> newSet = new HashSet<Character>();
				for(int j=0; j < json.getAncho(i); j++) {
					newSet.add( json.getDna()[i].charAt(j) );
				}
				if (!check.containsAll( newSet )) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad value of dna, is not G A T or C");
				}
			}
		}

		@PostConstruct
		public void executeInlyOneTime() {
			int humanDna = (int) ejercicioItemRepo.countByResult(false);
			int mutantDna = (int) ejercicioItemRepo.countByResult(true);
			cache.setCountHumanDna(humanDna);
			cache.setCountMutantDna(mutantDna);
		}
		
	}
