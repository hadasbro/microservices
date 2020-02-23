package com.github.hadasbro.stock_service.pojo;

import com.github.hadasbro.stock_service.udt.StoreUdt;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class Store {
    private String storeCatalogId;
    private String storeName;
    private String address;
    private String phone;

    /**
     * to
     *
     * @param fStore -
     * @return StoreUdt -
     */
    public static StoreUdt to(Store fStore) {
        StoreUdt sud = new StoreUdt();
        sud.setAddress(fStore.getAddress());
        sud.setPhone(fStore.getPhone());
        sud.setStoreCatalogId(fStore.getStoreCatalogId());
        sud.setStoreName(fStore.getStoreName());
        return sud;
    }
}
