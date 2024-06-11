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

	@Test
	void checkMatrixNoMutantOfSize4(){

		String string1 = "TGAC";
		String string2 = "GTAC";
		String string3 = "GACG";
		String string4 = "TGTC";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
	}

	@Test
	void checkMatrixNoMutantOfSize3(){

		String string1 = "TGA";
		String string2 = "GTA";
		String string3 = "GAC";

		String[] array = new String[]{
			string1,
			string2,
			string3
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
	}

	@Test
	void checkMatrixNoMutantOfSize2(){

		String string1 = "TG";
		String string2 = "AC";

		String[] array = new String[]{
			string1,
			string2
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
	}

	@Test
	void checkMatrixOfSize1Returns400(){

		String string1 = "T";
		String[] array = new String[]{
			string1
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	void checkMatrixNotSquare(){

		String string1 = "TGA";
		String string2 = "GTA";
		String string3 = "GAC";
		String string4 = "TGT";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	void checkMatrixNotSquare2(){

		String string1 = "TGAC";
		String string2 = "GTAC";
		String string3 = "GACG";

		String[] array = new String[]{
			string1,
			string2,
			string3
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	void checkMatrixContainsANull(){

		String string1 = "TGAC";
		String string2 = null;
		String string3 = "GACG";
		String string4 = "TGTC";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	void checkMatrixWithAEmptyString(){

		String string1 = "TGAC";
		String string2 = "GTAC";
		String string3 = "";
		String string4 = "TGTC";

		String[] array = new String[]{
			string1,
			string2,
			string3,
			string4
		};
	  
		JsonReceive json = new JsonReceive();
		json.setDna(array);

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	void checkMatrixWithoutDna(){
	  
		JsonReceive json = new JsonReceive();

		ResponseEntity<String> result = controller.isMutant(json);
		assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

}
