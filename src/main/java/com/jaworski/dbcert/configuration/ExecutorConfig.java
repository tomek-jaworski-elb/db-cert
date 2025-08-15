package com.jaworski.dbcert.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ExecutorConfig {

  @Bean(name = "ioExecutor")
  public Executor ioExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(10);       // liczba wątków utrzymywanych na stałe
    executor.setMaxPoolSize(20);        // maksymalna liczba wątków
    executor.setQueueCapacity(100);     // długość kolejki przed odrzuceniem zadań
    executor.setThreadNamePrefix("db-cert-io-");
    executor.initialize();              // musi być wywołane ręcznie
    return executor;
  }
}
