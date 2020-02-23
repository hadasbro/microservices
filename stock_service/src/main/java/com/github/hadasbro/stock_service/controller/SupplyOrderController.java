package com.github.hadasbro.stock_service.controller;

import com.github.hadasbro.stock_service.dto.EmailSupplyOrderDto;
import com.github.hadasbro.stock_service.dto.SupplyOrderBatchDto;
import com.github.hadasbro.stock_service.service.SupplyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * SupplyOrderController
 */
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/supply-orders")
public class SupplyOrderController {

    @Autowired
    private SupplyOrderService supplyOrderService;

    /**
     * handleSupply
     *
     * @param emailSupplyOrderDto -
     * @return Mono<SupplyOrderBatchDto>
     */
    @PostMapping("/supply-email-order")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SupplyOrderBatchDto> addOrderFromEmail(@RequestBody EmailSupplyOrderDto emailSupplyOrderDto) {
        return supplyOrderService.handleEmailOrder(emailSupplyOrderDto);
    }

}
