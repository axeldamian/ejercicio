package com.mercadolibre.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mercadolibre.dtos.Status;

@Service
public class MutantService {

	@Value("${spring.data.mongodb.uri}")
	private final String passw = null;

static Logger log = LogManager.getLogger(MutantService.class);
	
	private boolean chequearHorizontal(char[][] matrix, int positionX, int positionY){
		return positionY + 3 < matrix[0].length &&
				matrix[positionX][positionY] == (matrix[positionX][positionY+1]) && 
				matrix[positionX][positionY+1] == (matrix[positionX][positionY+2]) &&                                                                   
				matrix[positionX][positionY+2] == (matrix[positionX][positionY+3]);
	}
	
	private boolean chequearVertical(char[][] matrix, int positionX, int positionY){
		return positionX+3 < matrix.length &&
				matrix[positionX][positionY] == (matrix[positionX+1][positionY]) && 
				matrix[positionX+1][positionY] == (matrix[positionX+2][positionY]) &&                                                                   
				matrix[positionX+2][positionY] == (matrix[positionX+3][positionY]);
	}
	
	private boolean chequearDiagonalDerecha(char[][] matrix, int positionX, int positionY) {
		return positionX+3 < matrix.length && positionY+3 < matrix.length &&
				matrix[positionX][positionY] == (matrix[positionX+1][positionY+1]) &&
				matrix[positionX+1][positionY+1] == (matrix[positionX+2][positionY+2]) &&
				matrix[positionX+2][positionY+2] == (matrix[positionX+3][positionY+3]);
	}

	private boolean chequearDiagonalIzquierda(char[][] matrix, int positionX, int positionY) {
		return  positionX+3 <= matrix.length-1 && positionY-3 >=0 &&
				positionX+2 <= matrix.length-1 && positionY-2 >=0 &&
				positionX+1 <= matrix.length-1 && positionY-1 >=0 &&
				matrix[positionX][positionY] == matrix[positionX+1][positionY-1] &&
				matrix[positionX+1][positionY-1] == matrix[positionX+2][positionY-2] &&
				matrix[positionX+2][positionY-2] == matrix[positionX+3][positionY-3];
	}

	public boolean isMutant(String[] dna) {
		log.info(passw);
		char[][] matrix = getMatrix(dna);
		int contador = 0;

		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {

				if( chequearHorizontal(matrix, i, j) ) {
					contador = contador + 1;
				}

				if( chequearVertical(matrix, i , j) ) {
					contador = contador + 1;
				}
				if( chequearDiagonalDerecha(matrix, i , j) ) {
					contador = contador + 1;
				}

				if( chequearDiagonalIzquierda(matrix, i, j) ){
					contador = contador + 1;
				}
				
			}
		}

		if( contador >= 2 ) {
			log.info("es mutante por horizontal, vertical o diagonal");
			return true;
		}
		
		log.info("no es mutante");
		return false;
	}

	public Status status(int countHumanDna, int countMutantDna) {
		Status stat = new Status(countHumanDna, countMutantDna);
		return stat;
	}


		private char[][] getMatrix(String[] dna){
			char[][] matrix = new char[6][6];
			for(int i = 0 ; i < 6; i++){
				char[] arr = dna[i].toCharArray();
				for(int j = 0; j < 6; j++){
					matrix[i][j] =  arr[j];
				}
			}
			return matrix;
		}
    
}
