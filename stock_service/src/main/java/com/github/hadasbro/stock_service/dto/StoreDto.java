package com.github.hadasbro.stock_service.dto;


import com.github.hadasbro.stock_service.pojo.Store;
import com.github.hadasbro.stock_service.udt.StoreUdt;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * StoreDto
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@Data @NoArgsConstructor
public class StoreDto {
    private String storeCatalogId;
    private String storeName;
    private String address;
    private String phone;

    /**
     * from
     *
     * @param store -
     * @return StoreDto -
     */
    public static StoreDto from(Store store) {
        StoreDto sdto = new StoreDto();
        sdto.setAddress(store.getAddress());
        sdto.setPhone(store.getPhone());
        sdto.setStoreCatalogId(store.getStoreCatalogId());
        sdto.setStoreName(store.getStoreName());
        return sdto;
    }

    /**
     * from
     * @param store -
     * @return StoreDto -
     */
    public static StoreDto from(StoreUdt store) {
        StoreDto sdto = new StoreDto();
        sdto.setAddress(store.getAddress());
        sdto.setPhone(store.getPhone());
        sdto.setStoreCatalogId(store.getStoreCatalogId());
        sdto.setStoreName(store.getStoreName());
        return sdto;
    }
}
