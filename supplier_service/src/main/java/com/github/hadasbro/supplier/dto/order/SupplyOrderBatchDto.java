package com.github.hadasbro.supplier.dto.order;

import com.github.hadasbro.supplier.exception.OrderValidationException;
import com.github.hadasbro.supplier.model.SupplyOrderBatch;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "WeakerAccess"})
@Data
@NoArgsConstructor
final public class SupplyOrderBatchDto {

    private StoreDto store;
    private List<ProductBatchDto> products;
    private Date date;
    private STATUS status = STATUS.NONE;
    private String uuid;
    private OrderValidationException exception;

    public enum STATUS {
        NONE, ORDERED, READY_TO_SEND, PROVIDED
    }


    /**
     * getModelStatus
     *
     * @param status -
     * @return STATUS -
     * @throws IllegalArgumentException -
     */
    public static STATUS getModelStatus(SupplyOrderBatch.STATUS status) throws IllegalArgumentException {

        STATUS nStatus;

        switch (status) {
            case NONE:
                nStatus = STATUS.NONE;
                break;
            case ORDERED:
                nStatus = STATUS.ORDERED;
                break;
            case READY_TO_SEND:
                nStatus = STATUS.READY_TO_SEND;
                break;
            case PROVIDED:
                nStatus = STATUS.PROVIDED;
                break;
            default:
                throw new IllegalArgumentException("Invalid Status");
        }

        return nStatus;
    }

    /**
     * getModelStatus
     *
     * @param status -
     * @return SupplyOrderBatch.STATUS -
     * @throws IllegalArgumentException -
     */
    public static SupplyOrderBatch.STATUS getModelStatus(STATUS status) throws IllegalArgumentException {

        SupplyOrderBatch.STATUS nStatus;

        switch (status) {
            case NONE:
                nStatus = SupplyOrderBatch.STATUS.NONE;
                break;
            case ORDERED:
                nStatus = SupplyOrderBatch.STATUS.ORDERED;
                break;
            case READY_TO_SEND:
                nStatus = SupplyOrderBatch.STATUS.READY_TO_SEND;
                break;
            case PROVIDED:
                nStatus = SupplyOrderBatch.STATUS.PROVIDED;
                break;
            default:
                throw new IllegalArgumentException("Invalid Status");
        }

        return nStatus;
    }

    /**
     * from
     *
     * @param order -
     * @return SupplyOrderBatchDto -
     */
    public static SupplyOrderBatchDto from(SupplyOrderBatch order) {

        StoreDto storeDto = StoreDto.from(order.getStore());
        STATUS statusDto = getModelStatus(order.getStatus());
        List<ProductBatchDto> productsDto = order
                .getProducts()
                .stream()
                .map(ProductBatchDto::from)
                .collect(Collectors.toList());

        SupplyOrderBatchDto orderDto = new SupplyOrderBatchDto();
        orderDto.setStore(storeDto);
        orderDto.setStatus(statusDto);
        orderDto.setProducts(productsDto);
        orderDto.setDate(order.getDate());
        orderDto.setUuid(order.getUuid());

        return orderDto;

    }
}