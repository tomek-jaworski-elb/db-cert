package com.jaworski.dbcert.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaworski.dbcert.dto.StudentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collection;
import java.util.List;

@Component
public class StudentsRestClient {

    @Value("${rest.client.url}")
    private String restClientHost;

    private static final Logger LOG = LoggerFactory.getLogger(StudentsRestClient.class);
    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .readTimeout(Duration.ofSeconds(5))
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public void sendStudents(Collection<StudentDTO> students) throws JsonProcessingException, RestClientException {
        ResponseEntity<String> stringResponseEntity = restTemplate
                .postForEntity( restClientHost + "/api/v1" + "/student", students, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> result = objectMapper.readValue(stringResponseEntity.getBody(), new TypeReference<List<Integer>>() {
        });
        if (!result.isEmpty()) {
            LOG.info("New added students count: {}",result.size());
            LOG.info("New added students id: {}",result);
        }
    }
}
