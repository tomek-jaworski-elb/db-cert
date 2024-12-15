package com.jaworski.dbcert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DbCertApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DbCertApplication.class, args);
		InfoBean bean = context.getBean(InfoBean.class);
		bean.initInfo();
	}

}
