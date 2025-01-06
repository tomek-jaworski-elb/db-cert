package com.jaworski.dbcert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class InfoBean {

    private static final Logger LOG = LoggerFactory.getLogger(InfoBean.class);

    @Value("${task.fixedRate}")
    private String taskFixedRate;

    @Value("${task.initialDelay}")
    private String taskInitialDelay;

    @Value("${rest.client.url}")
    private String restClientHost;

    @Value("${db.file.path}")
    private String dbFilePath;

    @Value("${rest.client.custom.readtimeout}")
    private int readTimeout;

    @Value("${rest.client.custom.connecttimeout}")
    private int connectTimeout;

    public void initInfo() {
        LOG.info("Application started with profiles");
        LOG.info("Task fixed rate: {}", taskFixedRate);
        LOG.info("Task initial delay: {}", taskInitialDelay);
        LOG.info("Rest client host: {}", restClientHost);
        LOG.info("DB file path: {}", dbFilePath);
        LOG.info("Rest client read timeout: {}", readTimeout);
        LOG.info("Rest client connect timeout: {}", connectTimeout);
    }
}
