package com.github.hadasbro.stock_service.udt;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@RequiredArgsConstructor
@UserDefinedType("supplyudt")
public class SupplyUdt {
    private Date delivereddAt = new Date();
    private List<ProductBatchUdt> productBatchUdts = new ArrayList<>();
}