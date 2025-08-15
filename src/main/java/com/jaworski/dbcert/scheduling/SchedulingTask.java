package com.jaworski.dbcert.scheduling;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jaworski.dbcert.dto.InstructorDto;
import com.jaworski.dbcert.dto.StudentDTO;
import com.jaworski.dbcert.rest.AISRestClient;
import com.jaworski.dbcert.service.InstructorService;
import com.jaworski.dbcert.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

@RequiredArgsConstructor
@Component
public class SchedulingTask {
    private static final Logger LOG = LoggerFactory.getLogger(SchedulingTask.class);
    private final StudentService studentService;
    private final InstructorService instructorService;
    private final AISRestClient aisRestClient;
    private final Executor ioExecutor;

    public void scheduledTask() throws RestClientException {
      var startTime = LocalTime.now();
      LOG.info("Starting scheduled task");
      CompletableFuture<List<StudentDTO>> studentFuture = runAsync(studentService::getAllStudents);
      CompletableFuture<List<InstructorDto>> instructorFuture = runAsync(instructorService::getInstructors);

      CompletableFuture<Void> sendStudents = studentFuture.thenCompose(students -> {
        LOG.info("Read all students count: {}", students.size());
        LOG.info("Sending updated students");
        try {
          return aisRestClient.sendCollection(students);
        } catch (Exception e) {
          throw new RestClientException(e.getMessage());
        }
      });

      CompletableFuture<Void> sendInstructors = instructorFuture.thenCompose(instructors -> {
        LOG.info("Read instructors count: {}", instructors.size());
        LOG.info("Sending updated instructors");
        try {
          return aisRestClient.sendCollection(instructors);
        } catch (JsonProcessingException e) {
          throw new RestClientException(e.getMessage());
        }
      });

      CompletableFuture.allOf(sendStudents, sendInstructors)
              .whenComplete((ignored, ex) -> {
                if (ex != null) {
                  LOG.error("Task failed {}", ex.getMessage());
                  throw new RestClientException(ex.getMessage());
                } else {
                  LOG.info("All tasks finished in {} seconds",
                          (double) Duration.between(startTime, LocalTime.now()).toMillis() / 1_000);
                  System.exit(0);
                }
              });
    }

    private <T> CompletableFuture<List<T>> runAsync(CheckedSupplier<List<T>> supplier) {
      return CompletableFuture.supplyAsync(() -> {
        try {
          return supplier.get();
        } catch (Exception e) {
          throw new CompletionException(e);
        }
      }, ioExecutor);
    }

    @FunctionalInterface
    private interface CheckedSupplier<T> {
        T get() throws Exception;
    }
}
