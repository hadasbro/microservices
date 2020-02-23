package com.github.hadasbro.stock_service.model;

import com.github.hadasbro.stock_service.dto.SupplyBatchDto;
import com.github.hadasbro.stock_service.udt.ProductBatchUdt;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Data @NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table("supplies")
public class SupplyBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private String uuid;

    @Column("order_id")
    private Long orderId;

    @Column("products")
    private List<ProductBatchUdt> products;

    @Column("supply_date")
    private Date date;

    /**
     * fromSupplyBatchDto
     *
     * @param sbatch -
     * @return SupplyBatch -
     */
    public static SupplyBatch fromSupplyBatchDto(SupplyBatchDto sbatch) {
        SupplyBatch supplyBatch = new SupplyBatch();
        supplyBatch.setDate(sbatch.getDate());
        supplyBatch.setOrderId(sbatch.getOrderId());
        supplyBatch.setProducts(
                sbatch
                        .getProducts()
                        .stream()
                        .map(
                                ProductBatchUdt::fromProductBatchDto)
                        .collect(
                                Collectors.toList()
                        )
        );
        supplyBatch.setUuid(sbatch.getUuid());
        return supplyBatch;
    }
}
