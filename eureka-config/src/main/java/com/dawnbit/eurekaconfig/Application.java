package com.dawnbit.eurekaconfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
@EnableEurekaServer
@EnableAdminServer
@ComponentScan(basePackages = "com.dawnbit.common")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
