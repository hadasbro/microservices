package com.github.hadasbro.warehouse.domain;

import com.github.hadasbro.warehouse.dto.ProductBatchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@SuppressWarnings({"unused", "WeakerAccess"})
@Data
@NoArgsConstructor
@ToString
final public class ProductBatch {

    private String catalogId;

    private int quantity;

    /**
     * fromProductBatchDto
     *
     * @param productBatchDto -
     * @return ProductBatch -
     */
    public static ProductBatch fromProductBatchDto (ProductBatchDto productBatchDto) {
        ProductBatch pbatch = new ProductBatch();
        pbatch.setQuantity(productBatchDto.getQuantity());
        pbatch.setCatalogId(productBatchDto.getCatalogId());
        return pbatch;
    }
}