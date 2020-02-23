package com.github.hadasbro.email_order_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@SuppressWarnings({"unused"})
@Data @NoArgsConstructor
final public class SupplyOrderBatchDto {

    private StoreDto store;
    private List<ProductBatchDto> products;
    private Date date;
    private STATUS status = STATUS.NONE;
    private String uuid;

    public enum STATUS {
        NONE, ORDERED, READY_TO_SEND, PROVIDED
    }

}