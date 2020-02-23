package com.github.hadasbro.stock_service.dto;

import com.github.hadasbro.stock_service.model.SupplyOrderBatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data @NoArgsConstructor
final public class SupplyBatchDto {

    private StoreDto store;

    private Long orderId;

    private String uuid;

    private List<ProductBatchDto> products;

    private Date date;

}