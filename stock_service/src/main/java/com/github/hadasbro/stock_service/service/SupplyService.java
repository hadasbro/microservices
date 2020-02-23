package com.github.hadasbro.stock_service.service;

import com.github.hadasbro.stock_service.dto.ApiResponseDto;
import com.github.hadasbro.stock_service.model.Product;
import com.github.hadasbro.stock_service.model.SupplyBatch;
import com.github.hadasbro.stock_service.repository.OrderRepository;
import com.github.hadasbro.stock_service.repository.ProductRepository;
import com.github.hadasbro.stock_service.repository.SupplyBatchRepository;
import com.github.hadasbro.stock_service.repository.UserRepository;
import com.github.hadasbro.stock_service.service.feign.SupplierClient;
import com.github.hadasbro.stock_service.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SupplyOrderService
 */
@Service
@SuppressWarnings("unused")
public class SupplyService {

    @Autowired
    private ApploggerService apploggerService;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private EmailOrderService emailOrderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplyBatchRepository supplyBatchRepository;

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private OrderRepository orderRepository;

    /**
     * init
     */
    @PostConstruct
    public void init() {
    }

    /**
     * handleSupply
     *
     * @param supplyBatch -
     * @return ApiResponseDto
     */
    public Flux<ApiResponseDto> handleSupply(SupplyBatch supplyBatch) {

        try {

            List<Mono<Product>> x = supplyBatch
                    .getProducts()
                    .stream()
                    .map(pb -> {

                        String catalogId = pb.getCatalogId();
                        int qty = pb.getQuantity();

                        return productsRepository
                                .findByCatalogIdIn(
                                        Collections.singletonList(catalogId)
                                )
                                .single()
                                .map(prd -> {
                                    prd.setAvailableQuantity(
                                            prd.getAvailableQuantity() + qty
                                    );
                                    return prd;
                                });

                    })
                    .collect(Collectors.toList());

            return productsRepository
                    .saveAll(
                            Flux
                                    .merge(x)
                    )
                    .map(
                            e -> ApiResponseDto.ok()
                    );

        } catch (Throwable t) {
            apploggerService.log(t);
            return Flux.error(t);
        }

    }
}
