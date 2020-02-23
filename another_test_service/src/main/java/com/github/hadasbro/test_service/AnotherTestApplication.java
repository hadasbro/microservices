package com.github.hadasbro.test_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableEurekaClient
@EnableZipkinServer
public class AnotherTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnotherTestApplication.class, args);
    }
}
