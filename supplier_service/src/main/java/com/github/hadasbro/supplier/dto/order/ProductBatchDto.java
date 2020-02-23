package com.github.hadasbro.supplier.dto.order;

import com.github.hadasbro.supplier.model.ProductBatch;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings({"unused"})
@Data
@NoArgsConstructor
final public class ProductBatchDto {
    private String catalogId;
    private int quantity;

    /**
     * from
     *
     * @param productBatch -
     * @return ProductBatchDto -
     */
    public static ProductBatchDto from(ProductBatch productBatch) {
        ProductBatchDto pBatchDto = new ProductBatchDto();
        pBatchDto.setCatalogId(productBatch.getCatalogId());
        pBatchDto.setQuantity(productBatch.getQuantity());
        return pBatchDto;
    }

    /**
     * to
     *
     * @param productBatchDto -
     * @return ProductBatch -
     */
    public static ProductBatch to(ProductBatchDto productBatchDto) {
        ProductBatch pBatch = new ProductBatch();
        pBatch.setCatalogId(productBatchDto.getCatalogId());
        pBatch.setQuantity(productBatchDto.getQuantity());
        return pBatch;
    }
}