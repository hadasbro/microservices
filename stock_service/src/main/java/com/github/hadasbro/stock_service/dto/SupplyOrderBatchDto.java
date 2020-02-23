package com.github.hadasbro.stock_service.dto;

import com.github.hadasbro.stock_service.model.SupplyOrderBatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SupplyOrderBatchDto
 */
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

    /**
     * from
     *
     * @param so -
     * @return SupplyOrderBatchDto -
     */
    public static SupplyOrderBatchDto from (SupplyOrderBatch so) {
        SupplyOrderBatchDto sod = new SupplyOrderBatchDto();
        sod.setDate(so.getDate());
        sod.setProducts(so.getProducts().stream().map(ProductBatchDto::from).collect(Collectors.toList()));
        sod.setStatus(SupplyOrderBatch.statusTo(so));
        sod.setStore(StoreDto.from(so.getStore()));
        sod.setUuid(so.getUuid());
        return sod;
    }
}