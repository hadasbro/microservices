package com.github.hadasbro.supplier.config;

import com.github.hadasbro.supplier.event.CascadeSaveMongoEventListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * MongoReactiveConfig
 */
@SuppressWarnings("unused")
@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.github.hadasbro.supplier.repository")
public class MongoReactiveConfig {
    @Bean
    public CascadeSaveMongoEventListener cascadingMongoEventListener() {
        return new CascadeSaveMongoEventListener();
    }
}
