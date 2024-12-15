package com.jaworski.dbcert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DbCertApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbCertApplication.class, args);
	}

}
