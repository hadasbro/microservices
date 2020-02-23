package com.github.hadasbro.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.couchbase.repository.config.EnableReactiveCouchbaseRepositories;

@EnableHystrix
@SpringBootApplication(scanBasePackages = {"com.github.hadasbro.warehouse"})
@EnableReactiveCouchbaseRepositories(basePackages = "com.github.hadasbro.warehouse.repository")
@EnableEurekaClient
public class WarehouseApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}
