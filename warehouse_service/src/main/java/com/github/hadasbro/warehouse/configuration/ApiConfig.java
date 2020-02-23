package com.github.hadasbro.warehouse.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "clientapi")
public class ApiConfig {
    private String url_base;
    private String url_resource;
}