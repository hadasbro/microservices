package com.github.hadasbro.stock_service.controller;

import com.github.hadasbro.stock_service.dto.ApiResponseDto;
import com.github.hadasbro.stock_service.dto.SupplyBatchDto;
import com.github.hadasbro.stock_service.model.SupplyBatch;
import com.github.hadasbro.stock_service.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * SupplyOrderController
 */
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/supply")
public class SupplyController {

    @Autowired
    private SupplyService supplyService;

    /**
     * handleSupply
     *
     * @param supplyBatchDto -
     * @return Mono<ApiResponseDto>
     */
    @PostMapping("/handle")
    @ResponseStatus(HttpStatus.CREATED)
    public Flux<ApiResponseDto> handleSupply(@RequestBody SupplyBatchDto supplyBatchDto) {

        SupplyBatch sbatch = SupplyBatch.fromSupplyBatchDto(supplyBatchDto);

        return supplyService.handleSupply(sbatch);

    }

}
