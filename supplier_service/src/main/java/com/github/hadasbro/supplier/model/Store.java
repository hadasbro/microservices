package com.github.hadasbro.supplier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@SuppressWarnings("unused")
@Data
@Document
public class Store {

    @Id
    private String id;

    @Indexed(direction = IndexDirection.ASCENDING)
    private Long storeId;

    @Indexed(unique = true, direction = IndexDirection.ASCENDING)
    public String storeCatalogId;

    public String storeName;

    public String address;

    public String phone;

}
