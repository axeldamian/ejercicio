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
        log.info("added dna");
    }

    @Test
    void testingconcurrency() throws InterruptedException {
        
        Thread[] threads = new Thread[THREAD_COUNT];
        int real = 0;
    
        JsonReceive json = getJson();

        threads[0] = new Thread( () -> {
            addDnaToCache();
        } );

        threads[0].start(); // inicializo el primer hilo, que agrega dna.
        threads[0].join(); // espero que termine el 1er hilo, antes de ejecutar otra linea.

        for (int i = 1; i < THREAD_COUNT; i++) {
                threads[i] = new Thread( () -> {
                    String[] dna = json.getDna();
                    if( cache.stayInCache(dna) ){
                        integerConcurrent.incrementAndGet(); // incremento en 1 el entero concurrente.
                    }
            });
            threads[i].start(); // inicializo los siguientes 9 hilos.
        }

        try {
            for ( Thread thread : threads ) {
                if ( thread.getState() == State.TERMINATED || thread.getState() == State.RUNNABLE ){
                    real = real + 1; // si el estado del thread actual es terminado o ejecutando incremento en 1 real, que es int comun.
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 1; i < THREAD_COUNT; i++) {
            threads[i].join();
        }

        assertTrue( 0 <= real && real <= 10 ); // real son hilos terminados, debe ser siempre menor igual a 10.
        assertSame(10, real);
        assertSame(9, integerConcurrent.get()); // chequeo que pertenece el dna a la cache, ya que el primero solo agrega el dna.
    }

    @Test
    void testWithoutConcurrency(){
        JsonReceive json = getJson();
        cache.save(json.getDna(), false);
        assertTrue( cache.stayInCache( json.getDna() ) );
    }

    @Test
    void testingAnotherConcurrency() throws InterruptedException {

        Thread[] threads = new Thread[3];

        threads[0] = new Thread( () -> {
            String[] array = buildArray("GAT","ATC","CCC");
            cache.save(array, true);
        } );

        threads[1] = new Thread( () -> {
            String[] array = buildArray("GGG","ATC","CCC");
            cache.save(array, true);
        } );

        threads[2] = new Thread( () -> {
            String[] array = buildArray("GTC","ATC","CCC");
            cache.save(array, true);
        } );

        threads[0].start();
        threads[1].start();
        threads[2].start();

        cache.setCountHumanDna(0);
        cache.setCountMutantDna(3);

        threads[0].join();
        threads[1].join();
        threads[2].join();

       assertTrue( cache.stayInCache( new String[]{ "GAT", "ATC", "CCC"} ) );
       assertTrue( cache.stayInCache( new String[]{ "GGG", "ATC", "CCC"}) );
       assertTrue( cache.stayInCache( new String[]{ "GTC", "ATC", "CCC"}) );
    }

    private String[] buildArray(String string1, String string2, String string3){

        String[] array = new String[]{
			string1,
			string2,
			string3,
           };

           return array;
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
