package com.jaworski.dbcert.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaworski.dbcert.dto.InstructorDto;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.resources.CustomResources;
import com.jaworski.dbcert.rest.paths.Paths;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RequiredArgsConstructor
@Component
public class AISRestClientImpl implements AISRestClient {

    private final HttpClient httpClient;
    private final CustomResources customResources;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger LOG = LoggerFactory.getLogger(AISRestClientImpl.class);

    private String getAuthorizationHeaderValue(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    @Override
    public <T> void sendCollection(Collection<T> collection) throws JsonProcessingException, RestClientException {
        if (collection == null || collection.isEmpty()) {
            return;
        }
        Class<?> collectionType = collection.iterator().next().getClass();
        if (InstructorDto.class.isAssignableFrom(collectionType)) {
            send(collection, Paths.INSTRUCTORS);
        } else if (StudentDTO.class.isAssignableFrom(collectionType)) {
            send(collection, Paths.STUDENTS);
        } else {
            throw new RestClientException("Unsupported collection type: " + collectionType.getName());
        }
    }

    private <T> void send(Collection<T> collection, String path) throws JsonProcessingException, RestClientException {
        String className = collection.iterator().next().getClass().getName();
        try {
            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(customResources.getRestClientHost() + Paths.ROOT + path))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue(customResources.getUser(), customResources.getPassword()))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(collection)))
                    .build();

            HttpResponse<String> send = httpClient.sendAsync(build, HttpResponse.BodyHandlers.ofString())
                    .get(customResources.getReadTimeout(), TimeUnit.SECONDS);

            if (send.statusCode() != 200) {
                throw new RestClientException("Server respond with status code: " + send.statusCode());
            } else {
                LOG.info("{}", send.body() == null ? "" : send.body());
                List<Integer> result = objectMapper.readValue(send.body(), new TypeReference<List<Integer>>() {
                });
                if (!result.isEmpty()) {
                    LOG.info("New added {} count: {}", className, result.size());
                    LOG.info("New added {} id: {}", className, result);
                }
            }
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            LOG.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

}
