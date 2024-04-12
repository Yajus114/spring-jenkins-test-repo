package com.dawnbit.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dawnbit.storage.property.FileStorageProperties;

/**
 * Main Class
 */
@Configuration
@SpringBootApplication(scanBasePackages = "com.dawnbit")
@EnableJpaRepositories
@EntityScan("com.dawnbit.entity")
@EnableConfigurationProperties({FileStorageProperties.class})
public class Application {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
