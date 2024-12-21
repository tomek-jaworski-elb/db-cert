package com.jaworski.dbcert.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileDBUpdateChecker {


    @Value("${db.file.path}")
    private String filePath;

    private long lastUpdateTime = 0;

    public boolean isFileUpdated() {
        if (lastUpdateTime != updateLastUpdateTime()) {
            this.lastUpdateTime = updateLastUpdateTime();
            return true;
        }
        return false;
    }

    private long updateLastUpdateTime() {
        File file = new File(filePath);
        return file.lastModified();
    }
}
