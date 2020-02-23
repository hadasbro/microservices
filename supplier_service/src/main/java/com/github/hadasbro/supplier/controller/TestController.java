package com.github.hadasbro.supplier.controller;

import com.github.hadasbro.supplier.dto.order.ProductBatchDto;
import com.github.hadasbro.supplier.dto.order.StoreDto;
import com.github.hadasbro.supplier.dto.order.SupplyOrderBatchDto;
import com.github.hadasbro.supplier.dto.supply.SupplyBatchDto;
import com.github.hadasbro.supplier.exception.OrderValidationException;
import com.github.hadasbro.supplier.model.ProductBatch;
import com.github.hadasbro.supplier.model.Store;
import com.github.hadasbro.supplier.model.SupplyOrderBatch;
import com.github.hadasbro.supplier.repository.SupplyOrdersRepository;
import com.github.hadasbro.supplier.service.SupplyOrderService;
import com.github.hadasbro.supplier.service.SupplyService;
import com.github.hadasbro.supplier.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@Profile("test")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ApploggerService logService;

    @Autowired
    private SupplyOrderService supplyOrderService;

    @Autowired
    private SupplyOrdersRepository supplyOrdersRepository;

    @Autowired
    private SupplyService supplyService;

    /**
     * supplyTest
     * <p>
     * test method, only for profile=test
     */
    @GetMapping("/supply")
    public void supplyTest() {

        List<ProductBatch> products = new ArrayList<>();

        ProductBatch prod1 = new ProductBatch();
        prod1.setCatalogId("catalog1");
        prod1.setId("1");
        prod1.setQuantity(11);

        ProductBatch prod2 = new ProductBatch();
        prod2.setCatalogId("catalog2");
        prod2.setId("2");
        prod2.setQuantity(22);

        products.add(prod1);
        products.add(prod2);

        Store store = new Store();
        store.setAddress("Address");
        store.setId("3");
        store.setPhone("phone");
        store.setStoreCatalogId("catalog-id");
        store.setStoreName("store name");

        SupplyOrderBatch order = new SupplyOrderBatch();
        order.setDate(new Date());
        order.setId("4");
        order.setStatus(SupplyOrderBatch.STATUS.ORDERED);
        order.setStore(store);
        order.setProducts(products);
        order.setUuid("testuuid");

        List<ProductBatch> products_all = order.getProducts();

        List<ProductBatchDto> productsDto = products_all
                .stream()
                .map(ProductBatchDto::from)
                .collect(Collectors.toList());

        SupplyBatchDto supplyBatch = new SupplyBatchDto();
        supplyBatch.setProducts(productsDto);
        supplyBatch.setUuid(order.getUuid());
        supplyBatch.setDate(new Date());
        supplyBatch.setStore(StoreDto.from(order.getStore()));
        supplyBatch.setOrderId(2332L);

        supplyService.provideBatch(supplyBatch);

    }

    @GetMapping("/supply-order")
    public void supplyOrderTest() {

        Store store = new Store();
        store.setAddress("Address");
        store.setId("1");
        store.setPhone("phone");
        store.setStoreCatalogId("store-1-1581266062941");
        store.setStoreName("store name");

        List<ProductBatch> products = new ArrayList<>();

        ProductBatch prod1 = new ProductBatch();
        prod1.setCatalogId("catalog1");
        prod1.setId("1");
        prod1.setQuantity(11);

        ProductBatch prod2 = new ProductBatch();
        prod2.setCatalogId("catalog2");
        prod2.setId("2");
        prod2.setQuantity(22);

        products.add(prod1);
        products.add(prod2);

        List<ProductBatchDto> productsDto = products
                .stream()
                .map(ProductBatchDto::from)
                .collect(Collectors.toList());

        com.github.hadasbro.supplier.dto.order.SupplyOrderBatchDto sod = new SupplyOrderBatchDto();
        sod.setDate(new Date());
        sod.setStatus(SupplyOrderBatchDto.STATUS.NONE);
        sod.setStore(StoreDto.from(store));
        sod.setProducts(productsDto);


        try {

            SupplyOrderBatchDto orderResult = supplyOrderService.process(sod);

            if (orderResult.getException() != null) {
                throw orderResult.getException();
            }

        } catch (OrderValidationException ex) {
            logService.log(ex);
        }
    }

}