package com.mercadolibre.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CacheServiceTests {

    @Autowired
    CacheService service;

    @Test
    void testingCache() {
        String[] dna = {"AGT","CGT","CGT"};
        service.save( dna , true);
        if ( service.stayInCache(dna) ) {
         assertTrue(service.get(dna));
        }
    }

    @Test
    void resetCache(){
        service.resetAllData();
        assertTrue( service.getCountHumanDna().get() == service.getCountMutantDna().get() );
        assertEquals( 0, service.getCountHumanDna().get() );
    }
    
}
