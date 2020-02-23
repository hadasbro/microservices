package com.github.hadasbro.supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"com.github.hadasbro.supplier"})
@EnableMongoRepositories
@EnableFeignClients
@EnableHystrix
@EnableEurekaClient
public class SupplierApplication {
  public static void main(String[] args) {
    SpringApplication.run(SupplierApplication.class, args);
  }
}
