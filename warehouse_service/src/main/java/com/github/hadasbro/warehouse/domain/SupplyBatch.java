package com.github.hadasbro.warehouse.domain;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.github.hadasbro.warehouse.dto.SupplyBatchDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.couchbase.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Document
@Data
@NoArgsConstructor
@ToString
final public class SupplyBatch {

    @Id
    @Field
    @NotBlank
    private Long orderId;

    @Field
    @NotBlank
    private String storeCatalogId;

    @Field
    @NotBlank
    private String uuid;

    @Field
    private List<ProductBatch> products;

    @Field
    @NotBlank
    private Date date;

    /**
     * fromSupplyBatchDto
     *
     * @param supplyBatch -
     * @return SupplyBatch -
     */
    public static SupplyBatch fromSupplyBatchDto(SupplyBatchDto supplyBatch) {
        SupplyBatch sob = new SupplyBatch();
        sob.setDate(supplyBatch.getDate());
        sob.setOrderId(supplyBatch.getOrderId());
        sob.setStoreCatalogId(supplyBatch.getStore().getStoreCatalogId());
        sob.setUuid(supplyBatch.getUuid());
        sob.setProducts(
                supplyBatch
                        .getProducts()
                        .stream().map(
                        ProductBatch::fromProductBatchDto
                ).collect(
                        Collectors.toList()
                )
        );
        return sob;
    }
}