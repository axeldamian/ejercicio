package com.mercadolibre.services;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MutantServicesTests {

	static Logger log = LogManager.getLogger( MutantServicesTests.class);

	@Autowired
	private MutantService service;

	private String[] matrix = new String[]{};

	@BeforeEach
   public void setUp(){;
	  matrix = null;
   }

	@Test
	void checkMatrixNoMutant(){
		matrix = new String[] {
			"GTATAG",
			"TGGGTT",
			"AAGTAA",
			"AATTGG",
			"GGAATT",
			"CATTGG"
		};
		assertFalse(service.isMutant(matrix));
	}

	@Test
	void checkMatrixVerticalMutant(){
		log.info(matrix);
		matrix = new String[] {
			"GTATAG",
			"TGGTTT",
			"AAGTAA",
			"ATATGG",
			"GGAATT",
			"AATTGG"
		};
		boolean result = service.isMutant(matrix);
		assertTrue(result);
	}

	@Test
 void checkMatrixHorizontalMutant(){
		String[] matrix1 = new String[] {
			"GGATGA",
			"TGGGTT",
			"AAGTAA",
			"AAAAGG",
			"GGAATT",
			"AATTGG"
		};
		assertTrue(service.isMutant(matrix1));
	}

	@Test
	void checkAll(){
		String[] matrix = new String[] {
			"AAAAAA",
			"AAAAAA",
			"AAAAAA",
			"AAAAAA",
			"AAAAAA",
			"AAAAAA"
		};
		assertTrue(service.isMutant(matrix));
	}

	@Test
	void diagonalDerecha(){
		matrix = new String[] {
			"GTATAG",
			"TGGGTT",
			"AAGTAA",
			"AGTGGG",
			"GGAATT",
			"AATTGG"
		};
		assertTrue(service.isMutant(matrix));
	}

	@Test
	void diagonalIzquierda(){
		matrix = new String[] {
			"GTATAG",
			"ATGGTT",
			"ATGAAA",
			"AGTGGG",
			"GAATTT",
			"TATTGG"
		};
		assertTrue(service.isMutant(matrix));
	}

	@Test
	void cruzEsPositivo(){
		matrix = new String[] {
			"GTATAG",
			"TTGTTT",
			"ATTAAA",
			"ATTGGG",
			"TAATTT",
			"TATTGG"
		};
		assertTrue(service.isMutant(matrix));
	}

	@Test
	void checkMatrixNull(){
		matrix = null;
		assertThrows(NullPointerException.class , () -> {
		service.isMutant(matrix);
		});
	}

}
