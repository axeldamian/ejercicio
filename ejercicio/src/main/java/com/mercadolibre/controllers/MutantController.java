package com.mercadolibre.controllers;

import java.lang.reflect.Field;
import java.util.Arrays;
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
		public ResponseEntity<String> isMutant(@RequestBody JsonReceive json) 
		throws ResponseStatusException {

			ResponseEntity<String> checkRequest = checkRequest(json);
			if( checkRequest.getStatusCode() == HttpStatus.BAD_REQUEST ) {
				return checkRequest;
			}

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
			
			updateCacheCounters(result);

			if ( result ) {
				return new ResponseEntity<>("true", HttpStatus.OK);
			}

			return new ResponseEntity<>("false", HttpStatus.FORBIDDEN);
		}

		private ResponseEntity<String> response(boolean result) {
			if( result ) {
				return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
			}
			return new ResponseEntity<>(String.valueOf(result), HttpStatus.FORBIDDEN);
		}

		private ResponseEntity<String> updateCacheCounters(boolean value) {
			if( value ) {
				cache.getCountMutantDna().incrementAndGet();
				log.info("se actualizo la CACHE, el contador de mutantes");
				return response(value);
			} else {
				cache.getCountHumanDna().incrementAndGet();
				log.info("se actualizo la cache, el contador de humanos");
				return response(false);
			}
		}

		private ResponseEntity<String> checkRequest(JsonReceive json) {
			
			ResponseEntity<String> checkContainsDna = checkContainsDna(json);
			if ( checkContainsDna.getStatusCode() == HttpStatus.BAD_REQUEST ) {
				return checkContainsDna;
			}

			if ( json.getDna() == null ) {
				return new ResponseEntity<>("dna is null or is not present" , HttpStatus.BAD_REQUEST);
			}

			for( int i = 0; i < json.getLargo() ; i++ ) {
				if ( json.getDna()[i] == null ) {
					return new ResponseEntity<>("bad value of row " + ( i + 1 ) + " of dna, is null" , HttpStatus.BAD_REQUEST);
				}
			}
			
			for ( int i = 0 ; i < json.getLargo() ; i++ ) {
				if ( json.getLargo() != json.getDna()[i].length() ) {
					return new ResponseEntity<>("bad dna size, is not a square matrix" , HttpStatus.BAD_REQUEST);
				}
			}

			HashSet<Character> check = new HashSet<Character>();
			check.add('G');
			check.add('A');
			check.add('T');
			check.add('C');
			
			HashSet<Character> newSet = new HashSet<Character>();
			for(int i=0; i < json.getLargo(); i++) {
				for(int j=0; j < json.getAncho(i); j++) {
					newSet.add( json.getDna()[i].charAt(j) );
				}
			}

			if (!newSet.equals(check) || !check.containsAll( newSet )) {
				String msg = "values of matrix are not G A T C " + newSet.toString();
				log.info(msg);
				return new ResponseEntity<>("bad value of dna, is not G A T C" , HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<>("ok" , HttpStatus.OK);	
		}

		private ResponseEntity<String> checkContainsDna(JsonReceive json) {
			List<Field> properties = Arrays.asList(json.getClass().getDeclaredFields());
			for( Field property : properties ) {
				if( property.getName().equals("dna") ){
					return new ResponseEntity<>("ok" , HttpStatus.OK);
				}
			}

			log.info("no contains dna the request body json");
			return new ResponseEntity<>("no contain a field called dna" , HttpStatus.BAD_REQUEST);
		}

		@PostConstruct
		public void executeInlyOneTime() {
			int humanDna = (int) ejercicioItemRepo.countByResult(false);
			int mutantDna = (int) ejercicioItemRepo.countByResult(true);
			cache.setCountHumanDna(humanDna);
			cache.setCountMutantDna(mutantDna);
		}
		
	}
