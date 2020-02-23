package com.github.hadasbro.supplier.controller;

import com.github.hadasbro.supplier.dto.ApiResponseDto;
import com.github.hadasbro.supplier.dto.order.SupplyOrderBatchDto;
import com.github.hadasbro.supplier.exception.OrderValidationException;
import com.github.hadasbro.supplier.service.SupplyOrderService;
import com.github.hadasbro.supplier.service.SupplyService;
import com.github.hadasbro.supplier.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/supply")
public class OrderController {

    @Autowired
    private ApploggerService logService;

    @Autowired
    private SupplyOrderService supplyOrderService;

    @Autowired
    private SupplyService supplyService;

    /**
     * supplyOrder
     *
     * @param orderBatch -
     * @return ResponseEntity<ApiResponseDto> -
     */
    @PostMapping(
            path = "/order",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ApiResponseDto> supplyOrder(@RequestBody SupplyOrderBatchDto orderBatch) {

        try {

            SupplyOrderBatchDto orderResult = supplyOrderService.process(orderBatch);

            if (orderResult.getException() != null) {
                throw orderResult.getException();
            }

            return ResponseEntity
                    .ok(ApiResponseDto.ok());

        } catch (OrderValidationException ex) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(ApiResponseDto.from(ex));
        }

    }

    /**
     * heartbeat
     *
     * @return LocalDateTime
     */
    @GetMapping(
            path = "/heartbeat"
    )
    public LocalDateTime heartbeat() {
        return LocalDateTime.now();
    }
}