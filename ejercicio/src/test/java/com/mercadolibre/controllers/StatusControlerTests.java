package com.mercadolibre.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;

import com.mercadolibre.dtos.JsonReceive;
import com.mercadolibre.dtos.Status;

@SpringBootTest
class StatusControlerTests {
    
	@Autowired
	StatusController statusController;

	@Autowired
	ResetMongoController mongoController;

	@Autowired
	ResetCacheController cacheController;

	@Autowired
	MutantController mutantController;
   
     @Test
	void checkStatusAtInit(){
        assertSame(HttpStatusCode.valueOf(200) , statusController.status().getStatusCode());
	}

	@Test
   void checkStatusAfterAddedItems(){

		mongoController.resetMongoDB();
		cacheController.resetCache();

		String string1 = "TGAC";
		String string2 = "GTAC";
		String string3 = "CCCC";
		String string4 = "TGTC";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		mutantController.isMutant(json);

		assertSame(0, statusController.status().getBody().getCountHumanDna() );
		assertSame(1, statusController.status().getBody().getCountMutantDna() );
		assertEquals(Double.valueOf(0.0), Double.valueOf(statusController.status().getBody().getRatio()) );
	}

	@Test
	void modifyManuallyStatus(){
		Status stats = new Status();
		stats.setCountHumanDna(1);
		stats.setCountMutantDna(2);
		
		assertSame(1, stats.getCountHumanDna() );
		assertSame(2, stats.getCountMutantDna() );
		assertEquals(0.5, stats.getRatio() );

	}
    
}
