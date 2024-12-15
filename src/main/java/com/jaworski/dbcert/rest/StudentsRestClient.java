package com.jaworski.dbcert.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jaworski.dbcert.dto.StudentDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class StudentsRestClient {

    private static final Logger LOG = LogManager.getLogger(StudentsRestClient.class);
    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .readTimeout(Duration.ofSeconds(20))
            .connectTimeout(Duration.ofSeconds(20))
            .rootUri("http://localhost:8080")
            .build();

    public void sendStudents(Collection<StudentDTO> students) throws JsonProcessingException, RestClientException {
        ResponseEntity<String> stringResponseEntity = restTemplate
                .postForEntity("http://localhost:8080/api/students", students, String.class);
        LOG.info(stringResponseEntity.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> newAddedStudents = new ArrayList<>();
        List<Integer> result = objectMapper.readValue(stringResponseEntity.getBody(), new TypeReference<>() {
        });
        newAddedStudents.addAll(result);

        LOG.info("New added students: {}", newAddedStudents);
    }
}
