package com.github.hadasbro.supplier.service;

import com.github.hadasbro.supplier.dto.order.ProductBatchDto;
import com.github.hadasbro.supplier.dto.order.StoreDto;
import com.github.hadasbro.supplier.dto.order.SupplyOrderBatchDto;
import com.github.hadasbro.supplier.dto.supply.SupplyBatchDto;
import com.github.hadasbro.supplier.exception.OrderValidationException;
import com.github.hadasbro.supplier.model.ProductBatch;
import com.github.hadasbro.supplier.model.Store;
import com.github.hadasbro.supplier.model.SupplyOrderBatch;
import com.github.hadasbro.supplier.repository.StoreRepository;
import com.github.hadasbro.supplier.repository.SupplyOrdersRepository;
import com.github.hadasbro.supplier.service.graylog.ApploggerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.hadasbro.supplier.exception.OrderValidationException.CODE;

@SuppressWarnings({"unused", "WeakerAccess"})
@Service
public class SupplyOrderService {

    @Autowired
    private SupplyService supplyService;

    @Autowired
    private ApploggerService logService;

    @Autowired
    private SupplyOrdersRepository supplyOrdersRepository;

    @Autowired
    private StoreRepository storeRepository;

    /**
     * orderDtoToOrderModel
     *
     * @param supplyOrder -
     * @return SupplyOrderBatch -
     * @throws IllegalArgumentException -
     */
    private SupplyOrderBatch orderDtoToOrderModel(SupplyOrderBatchDto supplyOrder) throws OrderValidationException {

        try {

            SupplyOrderBatch supplyOrderBatch = new SupplyOrderBatch();
            supplyOrderBatch.setDate(supplyOrder.getDate());
            supplyOrderBatch.setStatus(SupplyOrderBatchDto.getModelStatus(supplyOrder.getStatus()));

            Optional<Store> store = storeRepository
                    .findOneByStoreCatalogId(supplyOrder.getStore().getStoreCatalogId());

            List<ProductBatch> products = supplyOrder
                    .getProducts()
                    .stream()
                    .map(ProductBatchDto::to)
                    .collect(Collectors.toList());

            supplyOrderBatch.setStore(store.orElseThrow(() -> new OrderValidationException("Store not found")));
            supplyOrderBatch.setProducts(products);
            supplyOrderBatch.setUuid(supplyOrder.getUuid());

            return supplyOrderBatch;

        } catch (IllegalArgumentException exc) {
            logService.log(exc);
            throw new OrderValidationException(exc.getMessage());
        } catch (Exception exc) {
            logService.log(exc);
            throw new OrderValidationException(exc.getMessage(), CODE.GENERAL);
        }

    }

    /**
     * process
     *
     * @param supplyOrder -
     * @return SupplyOrderBatchDto -
     * @throws IllegalArgumentException -
     */
    @HystrixCommand(
            fallbackMethod = "processFallback",
            commandProperties = {
                    @HystrixProperty(
                            name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "10000"),
                    @HystrixProperty(
                            name = "circuitBreaker.requestVolumeThreshold",
                            value = "30"),
                    @HystrixProperty(
                            name = "circuitBreaker.errorThresholdPercentage",
                            value = "20"),
                    @HystrixProperty(
                            name = "metrics.rollingStats.timeInMilliseconds",
                            value = "20000"),
                    @HystrixProperty(
                            name = "circuitBreaker.sleepWindowInMilliseconds",
                            value = "60000")
            })
    public SupplyOrderBatchDto process(SupplyOrderBatchDto supplyOrder) throws OrderValidationException {

        SupplyOrderBatch order = orderDtoToOrderModel(supplyOrder);

        // check products availability
        checkProducts(order.getProducts());

        // check Store
        checkStore(order.getStore());

        // save order request to database
        SupplyOrderBatch orderObj = supplyOrdersRepository.save(order);

        // do some other stuff ...
        // process order - supply ordered products to the client
        // just for test - process order immediately
        // normally order should be processed after some
        // time or pub-sub pattern should be used

        processSupply(orderObj);

        return supplyOrder;
    }

    /**
     * processFallback
     *
     * @return SupplyOrderBatchDto
     */
    public SupplyOrderBatchDto processFallback(SupplyOrderBatchDto supplyOrder, Throwable exc) {
        supplyOrder.setException(
                new OrderValidationException(
                        String.format("General process error [%s]", exc.toString() + exc.getMessage()
                        ), CODE.GENERAL
                ));
        return supplyOrder;
    }

    /**
     * processSupply
     * <p>
     * process rder, prepare products, send products to
     * the client/stock_service
     *
     * @param order -
     */
    public void processSupply(SupplyOrderBatch order) throws OrderValidationException {

        List<ProductBatchDto> products = order
                .getProducts()
                .stream()
                .map(ProductBatchDto::from)
                .collect(Collectors.toList());

        if (products.isEmpty()) {
            throw new OrderValidationException(CODE.ORDER_IS_EMPTY);
        }

        SupplyBatchDto supplyBatch = new SupplyBatchDto();
        supplyBatch.setDate(new Date());
        supplyBatch.setUuid(order.getUuid());
        supplyBatch.setProducts(products);
        supplyBatch.setStore(StoreDto.from(order.getStore()));

        supplyService.provideBatch(supplyBatch);

    }

    /**
     * checkStore
     * <p>
     * TODO
     *
     * @param store -
     * @throws OrderValidationException -
     */
    private void checkStore(Store store) throws OrderValidationException {

        // TODO - check all products and availability

        int cond = 1;

        if (cond > 1) {
            throw new OrderValidationException("Store unavailable", CODE.STORE_UNAVAILABLE);
        }

    }

    /**
     * checkProducts
     * <p>
     * TODO
     *
     * @param products -
     * @throws OrderValidationException -
     */
    private void checkProducts(List<ProductBatch> products) throws OrderValidationException {

        // TODO - check all products and availability
        int cond = 1;

        if (cond > 1) {
            throw new OrderValidationException("Product unavailable", CODE.PRODUCT_UNAVAILABLE);
        }

    }

}
