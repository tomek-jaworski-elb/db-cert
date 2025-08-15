package com.jaworski.dbcert.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.client.RestClientException;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface AISRestClient {

  <T> CompletableFuture<Void> sendCollection(Collection<T> collection) throws JsonProcessingException, RestClientException;
}
