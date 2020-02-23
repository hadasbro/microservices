package com.github.hadasbro.supplier.dto.supply;

import com.github.hadasbro.supplier.dto.order.ProductBatchDto;
import com.github.hadasbro.supplier.dto.order.StoreDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
final public class SupplyBatchDto {

    private Long orderId;

    private String uuid;

    private StoreDto store;

    private List<ProductBatchDto> products;

    private Date date;

}