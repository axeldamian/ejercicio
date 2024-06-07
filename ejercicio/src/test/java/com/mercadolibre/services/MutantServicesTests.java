package com.mercadolibre.services;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.dtos.Status;

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
	void checkMatrixVerticalMutantAndLastRowIsHorizontal(){
		log.info(matrix);
		matrix = new String[] {
			"GTATAG",
			"TGGTTT",
			"AAGTAA",
			"ATATGG",
			"GGAATT",
			"TTTTGG"
		};
		boolean result = service.isMutant(matrix);
		assertTrue(result);
	}

	@Test
 void checkMatrixHorizontalAndVerticalMutant(){
		String[] matrix1 = new String[] {
			"GAATGA",
			"TAGGTT",
			"AAGTAA",
			"AAAAGG",
			"GGAATT",
			"AATTGG"
		};
		assertTrue(service.isMutant(matrix1));
	}

	@Test
	void diagonalDerecha(){
		matrix = new String[] {
			"GTCTAG",
			"TGCCTT",
			"GAGCAA",
			"AGTGCG",
			"GGGATC",
			"CATGGG"
		};
		assertTrue(service.isMutant(matrix));
	}

	@Test
	void diagonalIzquierda(){
		matrix = new String[] {
			"GCATGA",
			"ATTAGC",
			"ATTCCA",
			"TTCTCG",
			"GCAGTT",
			"CATTGG"
		};
		assertTrue(service.isMutant(matrix));
	}

	@Test
	void cruzEsPositivo(){
		matrix = new String[] {
			"GTCTAG",
			"TCGTTT",
			"ACCCCA",
			"ATCCCG",
			"TACCTT",
			"TACCGG"
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

	@Test
	void checkMatrixOfExampleNoMutant(){
		matrix = new String[] {
			"ATGCGA",
			"CAGTGC",
			"TTATTT",
			"AGACGG",
			"GCGTCA",
			"TCACTG"
		};
		assertFalse(service.isMutant(matrix));
	}

	@Test
	void checkMatrixOfExampleMutant(){
		matrix = new String[] {
			"ATGCGA",
			"CAGTGC",
			"TTATGT",
			"AGAAGG",
			"CCCCTA",
			"TCACTG"
		};
		assertTrue(service.isMutant(matrix));
	}

	@Test
	void checkStatus(){
		  Status st = service.status(9, 18);
		  assertEquals(st.getCountHumanDna(), 9);
		  assertEquals(st.getCountMutantDna(), 18);
		  assertEquals(st.getRatio(), 0.50);
	   }

	   @Test
	   void checkOtherStatus(){
			 Status st = service.status(9, 18);
			 st.setCountHumanDna(10);
			 st.setCountMutantDna(5);
			 assertEquals(st.getRatio(), 2.00);
		  }

}
