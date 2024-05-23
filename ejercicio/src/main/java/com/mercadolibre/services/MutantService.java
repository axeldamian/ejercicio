package com.mercadolibre.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mercadolibre.dtos.Status;

@Service
public class MutantService {

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
	
	private boolean chequearDiagonal(char[][] matrix, int positionX, int positionY) {
		return positionX+3 < matrix.length && positionY+3 < matrix.length &&
				matrix[positionX][positionY] == (matrix[positionX+1][positionY+1]) &&
				matrix[positionX+1][positionY+1] == (matrix[positionX+2][positionY+2]) &&
				matrix[positionX+2][positionY+2] == (matrix[positionX+3][positionY+3]);
	}

	private boolean chequearOblicuosIzquierdo(char[][] matrix, int positionX, int positionY) {
		return positionX-3 >=0 && positionY-3 >=0 &&
				matrix[positionX][positionY] == matrix[positionX-1][positionY-1] &&
				matrix[positionX-1][positionY-1] == matrix[positionX-2][positionY-2] &&
				matrix[positionX-2][positionY-2] == matrix[positionX-3][positionY-3];
	}

	public boolean isMutant(String[] dna) {
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
				if( chequearDiagonal(matrix, i , j) ) {
					contador = contador + 1;
				}
				
			}
		}

		for(int k = matrix.length-4; k < matrix.length; k++) {
			for (int l = matrix[0].length-4; l < matrix.length; l++) {
				if ( chequearOblicuosIzquierdo(matrix, k, l) ) {
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
