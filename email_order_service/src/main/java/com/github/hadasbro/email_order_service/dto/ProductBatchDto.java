package com.github.hadasbro.email_order_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings({"unused"})
@Data @NoArgsConstructor
final public class ProductBatchDto {
    private String catalogId;
    private int quantity;
}