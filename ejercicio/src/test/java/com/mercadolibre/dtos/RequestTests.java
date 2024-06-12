package com.mercadolibre.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RequestTests {

    @Test
    void checkGetDna() {
        String[] array = {"AGTC", "CCCC", "GACC", "TTTC"};
        Request r = new Request(array, true);
        assertSame( array,r.getDna() );
        assertTrue( r.getResult() );

    }
    
}
