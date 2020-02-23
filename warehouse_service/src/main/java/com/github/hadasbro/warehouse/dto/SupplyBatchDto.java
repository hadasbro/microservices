package com.github.hadasbro.warehouse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
@ToString
final public class SupplyBatchDto {

    private StoreDto store;

    private Long orderId;

    private String uuid;

    private List<ProductBatchDto> products;

    private Date date;

}