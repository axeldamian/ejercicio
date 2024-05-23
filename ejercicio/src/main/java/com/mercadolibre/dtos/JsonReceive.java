package com.mercadolibre.dtos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonReceive {

static Logger log = LogManager.getLogger(JsonReceive.class);

    private String[] dna;

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }

    public int getLargo(){
        return dna.length;
    }

    public int getAncho(int index){
        return this.dna[index].length();
    }

}
