package com.github.hadasbro.test_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class AnotherTestConfig {

    @Value("${another_test.config.value3}")
    private int value;

    public int getValue(){
        return value;
    }

}
