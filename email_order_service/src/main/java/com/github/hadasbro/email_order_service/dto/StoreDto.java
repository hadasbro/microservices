package com.github.hadasbro.email_order_service.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings({"unused"})
@Data @NoArgsConstructor
public class StoreDto {
    private String storeCatalogId;
    private String storeName;
    private String address;
    private String phone;
}
