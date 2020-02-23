package com.github.hadasbro.stock_service.udt;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@RequiredArgsConstructor
@UserDefinedType("storeudt")
public class StoreUdt {
    private String storeCatalogId;
    private String storeName;
    private String address;
    private String phone;
}
