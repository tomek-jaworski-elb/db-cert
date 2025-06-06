package com.jaworski.dbcert.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.RestClientException;

import java.util.Collection;

public interface AISRestClient {

  <T> void sendCollection(Collection<T> collection) throws JsonProcessingException, RestClientException;
}
