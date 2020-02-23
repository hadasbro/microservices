package com.github.hadasbro.warehouse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings({"unused"})
@Data
@NoArgsConstructor
@ToString
final public class ProductBatchDto {
    private String catalogId;
    private int quantity;
}