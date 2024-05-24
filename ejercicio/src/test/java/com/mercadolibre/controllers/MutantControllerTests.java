package com.mercadolibre.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import com.mercadolibre.dtos.JsonReceive;

@SpringBootTest
class MutantControllerTests {

	static Logger log = LogManager.getLogger( MutantControllerTests.class);

	@Autowired
	MutantController controller;

	@BeforeEach
   public void setUp(){;
   }

	@Test
	void checkMatrixNoMutant(){

		String string1 = "TGACGA";
		String string2 = "GTACAT";
		String string3 = "GACGTG";
		String string4 = "TGTCAT";
		String string5 = "GACGAT";
		String string6 = "TAGTAC";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4,
			string5,
			string6
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<Boolean> result = controller.isMutant(json);
		assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
	}

	@Test
	void checkMatrixInvalid(){

		String string1 = "NGAGTC";
		String string2 = "GCAGAT";
		String string3 = "GAGCTG";
		String string4 = "CTTTAG";
		String string5 = "CTGATC";
		String string6 = "TAGCAG";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4,
			string5,
			string6
		};
	   JsonReceive json = new JsonReceive();
	   json.setDna(array);

	   assertThrows(ResponseStatusException.class, () -> {
		controller.isMutant(json);
	 });
	}

	@Test
	void checkMatrixInvalidSize(){

		String string1 = "TGACGA";
		String string2 = "GATACT";
		String string3 = "GGATCG";
		String string4 = "CATTTG";
		String string5 = "GATGCT";

	  String[] array = new String[]{
		string1,
		string2,
		string3,
		string4,
		string5
	  };

	   JsonReceive json = new JsonReceive();
	   json.setDna(array);
	   assertThrows(ResponseStatusException.class, () -> {
		   controller.isMutant(json);
		});
	}

	@Test
	void checkMatrixMaxSizeMutant(){

		String string1 = "TGACGA";
		String string2 = "GTACAT";
		String string3 = "GACGTG";
		String string4 = "TGTCAT";
		String string5 = "GACGAT";
		String string6 = "TAGTAC";
		String string7 = "ATGATC";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4,
			string5,
			string6,
			string7
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		assertThrows(ResponseStatusException.class, ()->{
			controller.isMutant(json);
		});
	}

	@Test
	void checkMatrixIsMutantWith5Equals(){

		String string1 = "TGACGA";
		String string2 = "GTACAT";
		String string3 = "GACGTG";
		String string4 = "TGTCAT";
		String string5 = "GACGAT";
		String string6 = "TAGTAC";
		String string7 = "ATGATC";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4,
			string5,
			string6,
			string7
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		assertThrows(ResponseStatusException.class, ()->{
			controller.isMutant(json);
		});
	}
}
