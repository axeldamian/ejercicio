package com.mercadolibre.dtos;

import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "countMutantDna", "countHumanDna", "ratio" })
public class Status {
    
    AtomicInteger countMutantDna;

    AtomicInteger countHumanDna;

    double ratio;

    public Status() {
        super();
        this.countHumanDna = new AtomicInteger(0);
        this.countMutantDna = new AtomicInteger(0);
        this.ratio = 0.0;
    }

    public Status(int humanDna, int mutantDna) {
        super();
        countHumanDna = new AtomicInteger(humanDna);
        countMutantDna = new AtomicInteger(mutantDna);
        ratio = 0.0;
    }

    @JsonGetter("count_mutant_dna")
    public int getCountMutantDna(){
        return countMutantDna.get();
    }

    @JsonGetter("count_human_dna")
    public int getCountHumanDna(){
        return countHumanDna.get();
    }

    public double getRatio(){
        if (countMutantDna.get()!=0){
            double num = countHumanDna.get();
            double denom = countMutantDna.get();
            double ratioVar = ( num / denom );
            this.ratio = ratioVar;
        }
        return this.ratio;
    }

    public void setCountHumanDna(int humanDna){
        this.countHumanDna = new AtomicInteger(humanDna);
    }

    public void setCountMutantDna(int mutantDna){
        this.countMutantDna = new AtomicInteger(mutantDna);
    }

    @Override
    public String toString() {
        super.toString();
        String s = "{ ";
        s = s + "count_human_dna:" + this.getCountHumanDna() + ",";
        s = s + "count_mutant_dna:" + this.getCountMutantDna() + ",";
        s = s + "ratio:" + this.getRatio() + " }";
        return s;
    }

}
