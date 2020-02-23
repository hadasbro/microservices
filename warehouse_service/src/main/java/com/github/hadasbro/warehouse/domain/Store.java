package com.github.hadasbro.warehouse.domain;

import com.couchbase.client.java.repository.annotation.Field;
import com.couchbase.client.java.repository.annotation.Id;
import com.github.hadasbro.warehouse.dto.StoreDto;
import lombok.*;
import org.springframework.data.couchbase.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@SuppressWarnings({"unused"})
@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@ToString
public class Store {

    @Id
    private String catalogId;

    @Field
    @NotBlank
    @Size(max = 100)
    private String storeName;

    @Field
    @NotBlank
    @Size(max = 150)
    private String address;

    @Field
    @NotBlank
    @Size(max = 30)
    private String phone;

    /**
     * fromStoreDto
     *
     * @param storeDto -
     * @return Store -
     */
    public static Store fromStoreDto(StoreDto storeDto) {
        Store store = new Store();
        store.setAddress(storeDto.getAddress());
        store.setCatalogId(storeDto.getStoreCatalogId());
        store.setPhone(storeDto.getPhone());
        store.setStoreName(storeDto.getStoreName());
        return store;
    }
}
