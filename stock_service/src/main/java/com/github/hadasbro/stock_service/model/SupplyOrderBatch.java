package com.github.hadasbro.stock_service.model;

import com.datastax.driver.core.utils.UUIDs;
import com.github.hadasbro.stock_service.dto.SupplyOrderBatchDto;
import com.github.hadasbro.stock_service.udt.ProductBatchUdt;
import com.github.hadasbro.stock_service.udt.StoreUdt;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
@Data @NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table("supply_orders")
public class SupplyOrderBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private String uuid = UUIDs.timeBased().toString();

    @Column("storeUdt")
    private StoreUdt store;

    @Column("products")
    private List<ProductBatchUdt> products;

    @Column("order_date")
    private Date date;

    private STATUS status = STATUS.NONE;

    public enum STATUS {
        NONE, ORDERED, READY_TO_SEND, PROVIDED
    }

    /**
     * statusFrom
     *
     * @param sod -
     * @return STATUS -
     */
    public static STATUS statusFrom(SupplyOrderBatchDto sod){
        switch (sod.getStatus()) {
            case ORDERED: return STATUS.ORDERED;
            case READY_TO_SEND: return STATUS.READY_TO_SEND;
            case PROVIDED: return STATUS.PROVIDED;
            case NONE:
            default: return STATUS.NONE;
        }
    }

    /**
     * statusFrom
     *
     * @param sod -
     * @return STATUS -
     */
    public static SupplyOrderBatchDto.STATUS statusTo(SupplyOrderBatch sod){
        switch (sod.getStatus()) {
            case ORDERED: return SupplyOrderBatchDto.STATUS.ORDERED;
            case READY_TO_SEND: return SupplyOrderBatchDto.STATUS.READY_TO_SEND;
            case PROVIDED: return SupplyOrderBatchDto.STATUS.PROVIDED;
            case NONE:
            default: return SupplyOrderBatchDto.STATUS.NONE;
        }
    }
}
