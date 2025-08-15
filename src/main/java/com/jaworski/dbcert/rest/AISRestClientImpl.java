package com.jaworski.dbcert.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaworski.dbcert.dto.InstructorDto;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.resources.CustomResources;
import com.jaworski.dbcert.rest.paths.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
public class AISRestClientImpl implements AISRestClient {

  private final HttpClient httpClient;
  private final CustomResources customResources;
  private final ObjectMapper objectMapper;

  private static final Logger LOG = LoggerFactory.getLogger(AISRestClientImpl.class);
  private static final Map<Class<?>, String> PATHS = Map.of(
          InstructorDto.class, Paths.INSTRUCTORS,
          StudentDTO.class, Paths.STUDENTS
  );

  public AISRestClientImpl(HttpClient httpClient, CustomResources customResources, @Qualifier("objectMapper") ObjectMapper objectMapper) {
    this.httpClient = httpClient;
    this.customResources = customResources;
    this.objectMapper = objectMapper;
  }

  private String getAuthorizationHeaderValue() {
    return "Basic " + Base64.getEncoder()
            .encodeToString((customResources.getUser() + ":" + customResources.getPassword()).getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public <T> CompletableFuture<Void> sendCollection(Collection<T> collection) throws RestClientException {
    if (collection == null || collection.isEmpty()) {
      return null;
    }

    var type = collection.iterator().next().getClass();
    var path = PATHS.get(type);
    if (path == null) {
      throw new RestClientException("Unsupported collection type: " + type.getName());
    }
    return send(collection, path);
  }

  private <T> CompletableFuture<Void> send(Collection<T> collection, String path) throws RestClientException {
    try {
      var request = HttpRequest.newBuilder()
              .uri(URI.create(customResources.getRestClientHost() + Paths.ROOT + path))
              .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue())
              .POST(HttpRequest.BodyPublishers.ofString(writeJson(collection)))
              .build();

      String entityName = PATHS.getOrDefault(collection.iterator().next().getClass(),"")
              .replace("/","")
              .toUpperCase();
      return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .thenAccept(response -> handleResponse(response, entityName))
              .exceptionally(e -> {
                if (e.getCause() instanceof IOException && "shutdownNow".equals(e.getCause().getMessage())) {
                  LOG.info("Request cancelled due to application shutdown");
                  stopAndExitApplication();
                  return null;
                }
                LOG.error("Request failed", e);
                stopAndExitApplication();
                return null;
              });
    } catch (Exception e) {
      throw new RestClientException("Failed to send request", e);
    }
  }

  private String writeJson(Object obj) throws RestClientException {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RestClientException("Failed to serialize JSON", e);
    }
  }

  private void handleResponse(HttpResponse<String> response, String className) {
    if (response.statusCode() < 200 || response.statusCode() >= 300) {
      throw new RestClientException("Server responded with status code: " + response.statusCode());
    }
    var body = response.body();
    if (body == null || body.isBlank()) {
      LOG.warn("Empty response body");
      return;
    }
    try {
      var result = objectMapper.readValue(body, new TypeReference<List<Integer>>() {});
      LOG.info("Added {} of {} entities: {}", result.size(), className, result);
    } catch (JsonProcessingException e) {
      LOG.error("Failed to parse server response", e);
      stopAndExitApplication();
    }
  }

  private static void stopAndExitApplication() {
    System.exit(1);
  }
}
