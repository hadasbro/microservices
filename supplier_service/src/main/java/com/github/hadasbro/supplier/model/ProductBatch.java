package com.github.hadasbro.supplier.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@SuppressWarnings("unused")
@Data
@Document
final public class ProductBatch {

    @Id
    private String id;

    private Long pbatchId;

    private String catalogId;

    private int quantity;

}