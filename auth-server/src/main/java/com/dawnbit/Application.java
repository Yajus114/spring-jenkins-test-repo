package com.dawnbit;

import com.dawnbit.security.config.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.SessionAttributes;


@SpringBootApplication
@EnableDiscoveryClient
@SessionAttributes("authorizationRequest")
@EntityScan("com.dawnbit.entity.master")
@EnableConfigurationProperties(RSAKeyRecord.class)
@EnableJpaRepositories
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
