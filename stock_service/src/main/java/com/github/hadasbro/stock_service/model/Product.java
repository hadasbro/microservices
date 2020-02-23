package com.github.hadasbro.stock_service.model;

import com.datastax.driver.core.utils.UUIDs;
import com.github.hadasbro.stock_service.udt.ManufacturerUdt;
import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@RestResource(rel = "products", path = "products")
@EqualsAndHashCode(of={"catalogId"})
@ToString(exclude = "manufacturerUdt")
@Table("products")
@Getter @Setter
public class Product {

    @PrimaryKeyColumn(
            name = "id",
            type = PrimaryKeyType.PARTITIONED,
            ordinal = 1
    )
    private UUID id = UUIDs.timeBased();

    private Date createdAt = new Date();

    private String name;

    private String description;

    @PrimaryKeyColumn(
            ordinal = 0,
            type = PrimaryKeyType.CLUSTERED
    )
    private String catalogId;

    private int availableQuantity;

    private ManufacturerUdt manufacturerUdt;

}
