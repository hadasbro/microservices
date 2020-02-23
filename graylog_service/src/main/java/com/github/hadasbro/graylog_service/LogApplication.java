package com.github.hadasbro.graylog_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * LogApplication
 */
@SpringBootApplication
@EnableEurekaClient
public class LogApplication {

  public static void main(String[] args) {
    SpringApplication.run(LogApplication.class, args);
  }

}
