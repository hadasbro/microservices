package com.github.hadasbro.stock_service.udt;

import com.github.hadasbro.stock_service.dto.ProductBatchDto;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@Data
@RequiredArgsConstructor
@UserDefinedType("productbatch")
public class ProductBatchUdt {
    private String catalogId;
    private int quantity;

    /**
     * fromProductBatchDto
     *
     * @param pbatch -
     * @return ProductBatchUdt
     */
    public static ProductBatchUdt fromProductBatchDto(ProductBatchDto pbatch) {
        ProductBatchUdt pbudt = new ProductBatchUdt();
        pbudt.setQuantity(pbatch.getQuantity());
        pbudt.setCatalogId(pbatch.getCatalogId());
        return pbudt;
    }
}