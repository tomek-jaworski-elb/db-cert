package com.jaworski.dbcert;

import com.jaworski.dbcert.resources.CustomResources;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class InfoBean {

    private static final Logger LOG = LoggerFactory.getLogger(InfoBean.class);
    private final CustomResources customResources;

    public void initInfo() {
        LOG.info("Application started with profiles");
        LOG.info("Task fixed rate: {}", customResources.getTaskFixedRate());
        LOG.info("Task initial delay: {}", customResources.getTaskInitialDelay());
        LOG.info("Rest client host: {}", customResources.getRestClientHost());
        LOG.info("DB file path: {}", customResources.getDbFilePath());
        LOG.info("Rest client read timeout: {}", customResources.getReadTimeout());
        LOG.info("Rest client connect timeout: {}", customResources.getConnectTimeout());
    }
}
