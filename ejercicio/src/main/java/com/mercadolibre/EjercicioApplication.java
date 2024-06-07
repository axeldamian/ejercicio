package com.mercadolibre;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class EjercicioApplication {

	private static final Logger log = LogManager.getLogger(EjercicioApplication.class);

	//@Value("${spring.data.mongodb.password}")
	//private String pass;
	public static void main(String[] args) {
		SpringApplication.run(EjercicioApplication.class, args);
		log.info(System.getenv("ENVIRONMENT_VAR"));
        log.info("Start Application");
		log.info("do a request!");
    }

}
