package com.github.hadasbro.stock_service.dto;

import com.github.hadasbro.stock_service.udt.ProductBatchUdt;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ProductBatchDto
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@Data @NoArgsConstructor
final public class ProductBatchDto {
    private String catalogId;
    private int quantity;

    /**
     * from
     *
     * @param productBatch -
     * @return ProductBatchDto -
     */
    public static ProductBatchDto from(ProductBatchUdt productBatch) {
        ProductBatchDto pbatch = new ProductBatchDto();
        pbatch.setCatalogId(productBatch.getCatalogId());
        pbatch.setQuantity(productBatch.getQuantity());
        return pbatch;
    }
}