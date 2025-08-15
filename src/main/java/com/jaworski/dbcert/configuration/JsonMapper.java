package com.jaworski.dbcert.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonMapper {

  @Bean
  @Qualifier("objectMapper")
  public ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
