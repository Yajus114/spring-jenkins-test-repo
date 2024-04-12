package com.dawnbit.storage.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * File storage properties
 */
@ConfigurationProperties(prefix = "file")
@Component
public class FileStorageProperties {
    /**
     * uploadDirectory
     */
    private String uploadDir;

    /**
     * public domain like http://localhost:8080
     */
    private String publicDomain;

    public String getUploadDir() {
        return this.uploadDir;
    }

    public void setUploadDir(final String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public String getPublicDomain() {
        return this.publicDomain;
    }

    public void setPublicDomain(final String publicDomain) {
        this.publicDomain = publicDomain;
    }
}
