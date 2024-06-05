package com.mercadolibre.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.dtos.Status;
import com.mercadolibre.services.CacheService;

@RestController
public class ResetCacheController {

    Logger log = LogManager.getLogger(ResetCacheController.class);

	CacheService cacheService;

	@Autowired
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

    @PostMapping(value = "/reset-cache", produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> resetCache() {
        Status stat = new Status(0, 0);
		try{
			cacheService.resetAllData();
		} catch ( Exception e ) {
			log.info(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(stat, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(stat, HttpStatus.OK);
	}

}
