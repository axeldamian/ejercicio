package com.mercadolibre;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories ("com.mercadolibre.mongo")
public class EjercicioApplication {

	private static final Logger log = LogManager.getLogger(EjercicioApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(EjercicioApplication.class, args);
		log.info("Start Application");
		log.info("do a request!");
	}

}
