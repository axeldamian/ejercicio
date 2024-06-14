package com.mercadolibre.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mercadolibre.dtos.JsonReceive;

@SpringBootTest
class CacheConcurrentTests extends Thread {

    Logger log = LogManager.getLogger(CacheConcurrentTests.class);

    AtomicInteger integerConcurrent = new AtomicInteger();

    private int THREAD_COUNT = 10;

    @Autowired
    CacheService cache;

    private void addDnaToCache() {
        JsonReceive json = getJson();
        cache.save(json.getDna(), false);
        integerConcurrent.incrementAndGet();
        log.info("added dna");
    }

    @Test
    void testingconcurrency() throws InterruptedException {
        AtomicInteger integerConc = new AtomicInteger(0);
        Thread[] threads = new Thread[THREAD_COUNT];
    
        JsonReceive json = getJson();

        threads[0] = new Thread( () -> {
            addDnaToCache();
        } );

        threads[0].start(); // inicializo el primer hilo, que agrega dna.

        for (int i = 1; i < THREAD_COUNT; i++) {
                threads[i] = new Thread( () -> {
                    String[] dna = json.getDna();
                    if( cache.stayInCache(dna) ){
                        integerConc.incrementAndGet();
                    }
            });
            threads[i].start(); // inicializo los siguientes 9 hilos.
        }

        try {
            for (int i = 0; i < THREAD_COUNT; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue( integerConc.get() > 2 ); // el hilo que agrega el dna y otro mas.

    }

    @Test
    void testWithoutConcurrency(){
        JsonReceive json = getJson();
        cache.save(json.getDna(), false);
        assertTrue( cache.stayInCache( json.getDna() ) );
    }

    private JsonReceive getJson() {

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
        return json;
    }
    
}
