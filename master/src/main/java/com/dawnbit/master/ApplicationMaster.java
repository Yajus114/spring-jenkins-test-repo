package com.dawnbit.master;

import com.dawnbit.common.security.jwtConfig.RSAKeyRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@EntityScan("com.dawnbit.entity.master")
@SpringBootApplication
@EnableJpaRepositories
@EnableConfigurationProperties(RSAKeyRecord.class)
@ComponentScan(basePackages = "com.dawnbit.entity.master,com.dawnbit.master,com.dawnbit.common,com.dawnbit.storage")
public class ApplicationMaster {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMaster.class, args);
    }
}
