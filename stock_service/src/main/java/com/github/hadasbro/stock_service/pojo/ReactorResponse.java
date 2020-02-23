package com.github.hadasbro.stock_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ReactorResponse<T> {
    private Throwable error;
    private T payload;
}
