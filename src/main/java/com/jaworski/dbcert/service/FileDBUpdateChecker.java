package com.jaworski.dbcert.service;

import com.jaworski.dbcert.resources.CustomResources;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@RequiredArgsConstructor
public class FileDBUpdateChecker {

    private final CustomResources customResources;
    private long lastUpdateTime = 0;

    public boolean isFileUpdated() {
        if (lastUpdateTime != updateLastUpdateTime()) {
            this.lastUpdateTime = updateLastUpdateTime();
            return true;
        }
        return false;
    }

    private long updateLastUpdateTime() {
        File file = new File(customResources.getDbFilePath());
        return file.lastModified();
    }
}
