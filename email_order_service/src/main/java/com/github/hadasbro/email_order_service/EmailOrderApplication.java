package com.github.hadasbro.email_order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * EmailOrderApplication
 */
@SpringBootApplication
@EnableEurekaClient
public class EmailOrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmailOrderApplication.class, args);
	}
}
