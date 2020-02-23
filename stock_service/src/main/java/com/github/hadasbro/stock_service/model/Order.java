package com.github.hadasbro.stock_service.model;

import com.datastax.driver.core.utils.UUIDs;
import com.github.hadasbro.stock_service.udt.ProductBatchUdt;
import com.github.hadasbro.stock_service.udt.UserUdt;
import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
@Data @NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table("orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    private UUID id = UUIDs.timeBased();

    @Column("order_date")
    private Date placedAt = new Date();

    @Column("userUdt")
    private UserUdt userUdt;

    @Column("products")
    private List<ProductBatchUdt> productBatchUdts = new ArrayList<>();

    private Type type;

    private Status status;

    public enum Type {
        IN_PLACE, REMOTE_TO_SEND, TO_COLLECT
    }

    public enum Status {
        COMPLETED, READY_TO_SEND, READY_TO_COLLECT, RECEIVED
    }

}
