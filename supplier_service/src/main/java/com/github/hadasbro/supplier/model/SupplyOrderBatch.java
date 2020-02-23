package com.github.hadasbro.supplier.model;

import com.github.hadasbro.supplier.annotation.CascadePersist;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@CompoundIndexes({
        @CompoundIndex(
                name = "sob_id_store_catalog_id",
                def = "{'sobId' : 1, 'store.storeCatalogId': 1}"
        )
})
@Document
final public class SupplyOrderBatch {

    @Id
    private String id;

    @DBRef
    private Store store;

    @CascadePersist
    @DBRef(lazy = true)
    private List<ProductBatch> products;

    private Date date;

    private STATUS status = STATUS.NONE;

    private String uuid;

    public enum STATUS {
        NONE, ORDERED, READY_TO_SEND, PROVIDED
    }
}