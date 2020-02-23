package com.github.hadasbro.stock_service.service;

import com.github.hadasbro.stock_service.config.StoreProperties;
import com.github.hadasbro.stock_service.dto.EmailSupplyOrderDto;
import com.github.hadasbro.stock_service.dto.SupplyOrderBatchDto;
import com.github.hadasbro.stock_service.model.SupplyOrderBatch;
import com.github.hadasbro.stock_service.pojo.Store;
import com.github.hadasbro.stock_service.repository.OrderRepository;
import com.github.hadasbro.stock_service.repository.ProductRepository;
import com.github.hadasbro.stock_service.repository.SupplyOrderBatchRepository;
import com.github.hadasbro.stock_service.repository.UserRepository;
import com.github.hadasbro.stock_service.service.feign.ApiResponseDto;
import com.github.hadasbro.stock_service.service.feign.SupplierClient;
import com.github.hadasbro.stock_service.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

/**
 * SupplyOrderService
 */
@Service
@SuppressWarnings("unused")
public class SupplyOrderService {

    private Store store;

    @Autowired
    private SupplierClient supplierClient;

    @Autowired
    private EmailOrderService emailOrderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplyOrderBatchRepository supplyOrderRepository;

    @Autowired
    private ProductRepository productsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StoreProperties properties;

    @Autowired
    private ApploggerService apploggerService;

    /**
     * init
     */
    @PostConstruct
    public void init() {
        this.store = new Store();
        this.store.setStoreName(properties.getName());
        this.store.setStoreCatalogId(properties.getCatalog_id());
        this.store.setPhone(properties.getPhone());
        this.store.setAddress(properties.getAddress());
    }

    /**
     * handleSupply
     *
     * @param orderBatch -
     * @return Mono<SupplyOrderBatch> -
     */
    public Mono<SupplyOrderBatch> orderSupply(SupplyOrderBatch orderBatch) {

        orderBatch.setStore(Store.to(this.store));

        return this.saveSupplyOrder(orderBatch)
                .flatMap(so -> {
                    orderBatch.setUuid(so.getUuid());
                    return this.sendSupplyOrder(orderBatch);
                });

    }

    /**
     * saveSupplyOrder
     *
     * @param orderBatch -
     * @return Mono<SupplyOrderBatch> -
     */
    private Mono<SupplyOrderBatch> saveSupplyOrder(SupplyOrderBatch orderBatch) {
        return supplyOrderRepository.save(orderBatch);
    }

    /**
     * sendSupplyOrder
     *
     * @param orderBatch -
     * @return Mono<SupplyOrderBatch> -
     */
    private Mono<SupplyOrderBatch> sendSupplyOrder(SupplyOrderBatch orderBatch) {

        SupplyOrderBatchDto so = SupplyOrderBatchDto.from(orderBatch);

        ApiResponseDto resp = supplierClient.orderSupply(so);

        if (resp.getCode() != ApiResponseDto.CODE.OK) {
            return Mono.error(new Exception(resp.getMessage()));
        }

        return Mono.just(orderBatch);
    }

    /**
     * @param emailSupplyOrderDto -
     * @return Mono<SupplyOrderBatchDto> -
     */
    public Mono<SupplyOrderBatchDto> handleEmailOrder(EmailSupplyOrderDto emailSupplyOrderDto) {

        return emailOrderService
                .handleSupplyOrder(emailSupplyOrderDto);

    }
}
