package com.mercadolibre.services;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.mercadolibre.dtos.Status;

@Component
@Service
public class CacheService {
    
    Logger log = LogManager.getLogger(CacheService.class);
    
    private ConcurrentHashMap< Integer[] , Boolean > dicc = new ConcurrentHashMap<>();

    private AtomicInteger countHumanDna = new AtomicInteger(0);

    private AtomicInteger countMutantDna = new AtomicInteger(0);

    public boolean stayInCache(String[] dna){
        log.info("consulta a la memoria CACHE");
        return dicc.containsKey(valueOf(dna));
    }

    public boolean get(String[] dna){
        Integer[] k = valueOf(dna);
        Boolean result = dicc.get(k);
        log.info("se recupero el resultado de la CACHE");
        return result.booleanValue();
    }

    public void save(String[] dna, boolean result) {
        Integer[] key = valueOf(dna);
        dicc.putIfAbsent(key, result);
        log.info("se guardo el dato en memoria CACHE");
    }

    public void resetAllData() {
        setCountHumanDna(0);
        setCountMutantDna(0);
        dicc.clear();
        log.info("se reinicio la memoria CACHE");
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

    private Integer[] valueOf(String[] dna) {

        HashMap<Character, Integer> values = new HashMap<>();
        values.put('A', 1);
        values.put('T', 2);
        values.put('G', 3);
        values.put('C', 4);

        Integer[] array = new Integer[dna.length];

        for (int i = 0 ; i < dna.length ; i++) {
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
        return array;
    }
}
