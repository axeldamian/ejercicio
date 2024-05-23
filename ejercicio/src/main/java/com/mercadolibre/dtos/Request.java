package com.mercadolibre.dtos;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("ejercicio")
public class Request {

    private String[] dna;

    private boolean result;

    public Request(String[] dna,boolean result){
        this.dna = dna;
        this.result = result;
    }

    public String[] getDna(){
        return this.dna;
    }

    public boolean getResult(){
        return this.result;
    }

}
