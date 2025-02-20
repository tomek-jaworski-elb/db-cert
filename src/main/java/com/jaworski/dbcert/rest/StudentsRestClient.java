package com.jaworski.dbcert.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaworski.dbcert.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class StudentsRestClient {

    @Value("${rest.client.url}")
    private String restClientHost;

    @Value("${rest.client.custom.readtimeout}")
    private int readTimeout;

    @Value("${rest.client.custom.connecttimeout}")
    private int connectTimeout;

    @Value("${http.client.username}")
    private String user;

    @Value("${http.client.password}")
    private String password;

    private final HttpClient httpClient;

    private static final Logger LOG = LoggerFactory.getLogger(StudentsRestClient.class);

    private String getAuthorizationHeaderValue(String username, String password) {
        return "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
    }

    public void sendStudents(Collection<StudentDTO> students) throws JsonProcessingException, RestClientException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            HttpRequest build = HttpRequest.newBuilder()
                    .uri(URI.create(restClientHost + "/api/v1" + "/student"))
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .header(HttpHeaders.AUTHORIZATION, getAuthorizationHeaderValue(user, password))
                    .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(students)))
                    .build();

            HttpResponse<String> send = httpClient.sendAsync(build, HttpResponse.BodyHandlers.ofString())
                    .get(readTimeout, TimeUnit.SECONDS);

            if (send.statusCode() != 200) {
                throw new RestClientException("Server respond with status code: " + send.statusCode());
            } else {
                LOG.info("{}", send.body());
                List<Integer> result = objectMapper.readValue(send.body(), new TypeReference<List<Integer>>() {
                });
                if (!result.isEmpty()) {
                    LOG.info("New added students count: {}", result.size());
                    LOG.info("New added students id: {}", result);
                }
            }
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
