package com.dawnbit.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.dawnbit.apigateway")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
