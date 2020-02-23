package com.github.hadasbro.supplier.dto.order;

import com.github.hadasbro.supplier.model.Store;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("unused")
@Data
@NoArgsConstructor
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
        StoreDto storeDto = new StoreDto();
        storeDto.setAddress(store.getAddress());
        storeDto.setPhone(store.getPhone());
        storeDto.setStoreCatalogId(store.getStoreCatalogId());
        storeDto.setStoreName(store.getStoreName());
        return storeDto;
    }
}
