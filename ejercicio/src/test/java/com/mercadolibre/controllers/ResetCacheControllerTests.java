package com.mercadolibre.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.dtos.Status;

@SpringBootTest
class ResetCacheControllerTests {

    @Autowired
	ResetCacheController controller;

    @Autowired
	ResetMongoController mongoController;

    @Autowired
	StatusController statusController;

    @BeforeEach
    public void setUp(){;
    }

   @Test
   void checkReset(){
        Status stats = new Status();
        mongoController.resetMongoDB();
		controller.resetCache();
        assertSame(stats.getCountHumanDna(), statusController.status().getBody().getCountHumanDna());
        assertSame(stats.getCountMutantDna(), statusController.status().getBody().getCountMutantDna());
        assertTrue(stats.getRatio() == statusController.status().getBody().getRatio());
   }
    
}
