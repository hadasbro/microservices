package com.github.hadasbro.warehouse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings("unused")
@Data
@NoArgsConstructor
@ToString
public class StoreDto {
    private String storeCatalogId;
    private String storeName;
    private String address;
    private String phone;
}
