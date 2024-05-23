package com.mercadolibre.services;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Sextet;
import org.springframework.stereotype.Service;

import com.mercadolibre.dtos.Status;

@Service
public class CacheService {
    
    Logger log = LogManager.getLogger(CacheService.class);

    private ConcurrentHashMap< Sextet<Integer, Integer, Integer, Integer, Integer, Integer> , Boolean> dicc = new ConcurrentHashMap<>();
    
    private AtomicInteger countHumanDna = new AtomicInteger(0);

    private AtomicInteger countMutantDna = new AtomicInteger(0);

    public boolean stayInCache(String[] dna){
        log.info("consulta a la memoria CACHE");
        return dicc.containsKey(valueOf(dna));
    }

    public boolean get(String[] dna){
        Sextet<Integer, Integer, Integer, Integer, Integer, Integer> k = valueOf(dna);
        Boolean result = dicc.get(k);
        log.info("se recupero el resultado de la CACHE");
        return result.booleanValue();
    }

    public void save(String[] dna, boolean result){
        Sextet<Integer, Integer, Integer, Integer, Integer, Integer> keySextet = valueOf(dna);
        dicc.putIfAbsent(keySextet, result);
        log.info("se guardo el dato en memoria CACHE");
    }

    public AtomicInteger getCountHumanDna() {
        return this.countHumanDna;
    }

    public AtomicInteger getCountMutantDna() {
        return this.countMutantDna;
    }

    public void setCountHumanDna(int countHuman) {
        this.countHumanDna = new AtomicInteger(countHuman);
    }

    public void setCountMutantDna(int mutantDna) {
        this.countMutantDna = new AtomicInteger(mutantDna);
    }

    public Status getCurrentStatus(int countHuman, int countMutant) {
        Status status = new Status();
        status.setCountHumanDna(countHuman);
        status.setCountMutantDna(countMutant);
        return status;
    }

    private Sextet<Integer, Integer, Integer, Integer, Integer, Integer> valueOf(String[] dna){
        HashMap<Character, Integer> values = new HashMap<>();
        values.put('A', 1);
        values.put('T', 2);
        values.put('G', 3);
        values.put('C', 4);

        Integer[] array = new Integer[6];
        for (int i = 0 ; i < 6 ; i++) {
            
            int h = 1;
            for(int j = 0; j < dna.length; j++) {
                char c = dna[i].charAt(j);
                if( values.get(c) == null ) {
                    h = h * 0;
                } else {
                    h = h * values.get(c);
                }
            }
            array[i] = Integer.valueOf(h);
        }
        return Sextet.fromArray(array);
    }
}
