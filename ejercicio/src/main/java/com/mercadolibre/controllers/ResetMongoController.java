package com.mercadolibre.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.mongo.ItemRepository;

@RestController
public class ResetMongoController {

    Logger log = LogManager.getLogger(ResetMongoController.class);

    ItemRepository ejercicioMongoItemRepo;

	@Autowired
	public void setMongoDBRepository(ItemRepository ejercicioMongoItemRepo) {
		this.ejercicioMongoItemRepo = ejercicioMongoItemRepo;
	}

    @PostMapping(value = "/reset-mongo", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> resetMongoDB() {
		try{
			ejercicioMongoItemRepo.deleteAll();
		} catch ( Exception e ) {
			return new ResponseEntity<>("hubo un error", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("se borro bien mongo", HttpStatus.OK);
	}

}
