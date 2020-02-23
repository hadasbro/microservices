package com.github.hadasbro.stock_service.service.feign;

import com.github.hadasbro.stock_service.dto.SupplyOrderBatchDto;
import com.github.hadasbro.stock_service.service.graylog.ApploggerService;
import feign.hystrix.FallbackFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@SuppressWarnings({"unused"})
@FeignClient(
        name = "${supplier_service_name}",
        fallbackFactory = SupplierClient.HystrixClientFallbackFactory.class
)
public interface SupplierClient {

    /**
     * heartbeat service
     *
     * @return LocalDateTime
     */
    @GetMapping("/supply/heartbeat")
    LocalDateTime heartbeat();

    /**
     * handleSupply service
     *
     * @param orderBatch -
     * @return ApiResponseDto
     */
    @PostMapping("/supply/order")
    ApiResponseDto orderSupply(@RequestBody SupplyOrderBatchDto orderBatch);

    /**
     * HystrixClientFallbackFactory
     */
    @Component
    class HystrixClientFallbackFactory implements FallbackFactory<SupplierClient> {

        @Autowired
        private ApploggerService apploggerService;

        /**
         * SupplierClient
         * @param cause -
         * @return SupplierClient
         */
        @Override
        public SupplierClient create(Throwable cause) {
            return new SupplierClient() {

                @Override
                public LocalDateTime heartbeat() {

                    apploggerService.log("Supplier Client heartbeat inaccessible");

                    return null;
                }

                @Override
                public ApiResponseDto orderSupply(SupplyOrderBatchDto orderBatch) {

                    apploggerService.log(cause);

                    return new ApiResponseDto(cause.getMessage(), ApiResponseDto.CODE.GENERAL);
                }

            };
        }
    }

}


