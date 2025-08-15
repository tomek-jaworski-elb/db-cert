package com.jaworski.dbcert;

import com.jaworski.dbcert.scheduling.SchedulingTask;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DbCertApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DbCertApplication.class, args);
		InfoBean bean = context.getBean(InfoBean.class);
    SchedulingTask schedulingTask = context.getBean(SchedulingTask.class);
    schedulingTask.scheduledTask();
    bean.initInfo();
  }

}
