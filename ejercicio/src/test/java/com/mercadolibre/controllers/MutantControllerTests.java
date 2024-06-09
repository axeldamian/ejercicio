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

		ResponseEntity<String> result = controller.isMutant(json);
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

	   assertSame( true , controller.isMutant(json).getStatusCode() == HttpStatus.BAD_REQUEST );
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
	   assertSame( true , controller.isMutant(json).getStatusCode() == HttpStatus.BAD_REQUEST );
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
		assertSame( true ,  controller.isMutant(json).getStatusCode() == HttpStatus.BAD_REQUEST );
	}

	@Test
	void checkMatrixIsMutantWith5EqualsIsMutant(){

		String string1 = "TGACGA";
		String string2 = "GTACAT";
		String string3 = "CCCCCT";
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

		assertEquals(HttpStatus.OK, controller.isMutant(json).getStatusCode() );
	}


	@Test
	void checkAllAIsNoValid(){

		String string1 = "AAAAAA";
		String string2 = "AAAAAA";
		String string3 = "AAAAAA";
		String string4 = "AAAAAA";
		String string5 = "AAAAAA";
		String string6 = "AAAAAA";

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
		assertSame( true , controller.isMutant(json).getStatusCode() == HttpStatus.BAD_REQUEST );
	}

}
