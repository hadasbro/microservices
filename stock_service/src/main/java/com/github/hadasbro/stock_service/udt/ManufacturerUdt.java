package com.github.hadasbro.stock_service.udt;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@UserDefinedType("manufacturer")
@RequiredArgsConstructor
public class ManufacturerUdt {
    private String companyName;
    private String address;
}
