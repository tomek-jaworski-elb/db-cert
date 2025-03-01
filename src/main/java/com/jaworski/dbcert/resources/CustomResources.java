package com.jaworski.dbcert.resources;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Getter
public class CustomResources {

    @Value("${http.client.password}")
    private String password;

    @Value("${http.client.username}")
    private String user;

    @Value("${db.file.path}")
    private String dbFilePath;

    @Value("${task.fixedRate}")
    private String taskFixedRate;

    @Value("${task.retryCountMax}")
    private int taskRetryCountMax;

    @Value("${rest.client.custom.readtimeout}")
    private int readTimeout;

    @Value("${rest.client.custom.connecttimeout}")
    private int connectTimeout;

    @Value("${rest.client.url}")
    private String restClientHost;

    @Value("${task.initialDelay}")
    private String taskInitialDelay;
}
