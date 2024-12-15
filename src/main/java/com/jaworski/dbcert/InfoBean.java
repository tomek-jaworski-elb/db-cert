package com.jaworski.dbcert;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InfoBean {

    private static final Logger LOG = LogManager.getLogger(InfoBean.class);

    @Value("${task.fixedRate}")
    private String taskFixedRate;

    @Value("${task.initialDelay}")
    private String taskInitialDelay;

    @Value("${rest.client.url}")
    private String restClientHost;

    @Value("${db.file.path}")
    private String dbFilePath;


    public void initInfo() {
        LOG.info("Application started with profiles");
        LOG.info("Task fixed rate: {}", taskFixedRate);
        LOG.info("Task initial delay: {}", taskInitialDelay);
        LOG.info("Rest client host: {}", restClientHost);
        LOG.info("DB file path: {}", dbFilePath);
    }
}
