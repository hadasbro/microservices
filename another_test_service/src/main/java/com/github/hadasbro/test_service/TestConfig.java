package com.github.hadasbro.test_service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * TestConfig
 */
@SuppressWarnings({"unused"})
@Configuration
@Getter @Setter
@ConfigurationProperties(prefix="test.config")
class TestConfig {
    private int value;
    private int value2;
}