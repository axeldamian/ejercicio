package com.mercadolibre.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.dtos.Status;
import com.mercadolibre.mongo.ItemRepository;
import com.mercadolibre.services.CacheService;
import com.mercadolibre.services.MutantService;

@RestController
public class StatusController {

    Logger log = LogManager.getLogger(StatusController.class);

    @Autowired
	private MutantService service;

	@Autowired
    ItemRepository ejercicioItemRepo;

	@Autowired
	CacheService cache;

    @GetMapping(value = "/stats", produces= MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Status> status() {
	Query queryHumancount = new Query();
	queryHumancount.addCriteria(Criteria.where("result").is(true));
		Document query = new Document();
		query.put("result", false);

		long countHumanDna = ejercicioItemRepo.countByResult(false);
		long countMutantDna = ejercicioItemRepo.countByResult(true);

		Status stat = service.status((int) countHumanDna, (int) countMutantDna);
		ObjectMapper om = new ObjectMapper();
        
        try {
            String json = om.writeValueAsString(stat);
            log.info(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
		return new ResponseEntity<>(stat, HttpStatus.OK);
	}

}
