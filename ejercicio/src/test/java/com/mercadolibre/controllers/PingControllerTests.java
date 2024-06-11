package com.mercadolibre.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PingControllerTests {

	static Logger log = LogManager.getLogger(PingControllerTests.class);

	@Autowired
	PingController controller;

	@BeforeEach
   public void setUp(){;
   }

   @Test
   void checkPing(){
		String result = controller.pingGet();
		assertEquals("pong", result);
   }

}
