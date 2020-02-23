package com.github.hadasbro.warehouse.controllers;

import com.github.hadasbro.warehouse.dto.ProductBatchDto;
import com.github.hadasbro.warehouse.dto.StoreDto;
import com.github.hadasbro.warehouse.dto.SupplyBatchDto;
import com.github.hadasbro.warehouse.service.SupplyHandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
//@Profile("test")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SupplyHandleService handleService;

    /**
     * supplyTest
     */
    @GetMapping("/test-handle")
    public void supplyTest() {

        List<ProductBatchDto> products = new ArrayList<ProductBatchDto>(){{

            ProductBatchDto product1 = new ProductBatchDto();
            product1.setCatalogId("product#1");
            product1.setQuantity(12);

            ProductBatchDto product2 = new ProductBatchDto();
            product1.setCatalogId("product#2");
            product1.setQuantity(15);

            add(product1);
            add(product2);

        }};

        StoreDto store = new StoreDto();
        store.setAddress("Address");
        store.setPhone("123-456-789");
        store.setStoreCatalogId("store-catalog1");
        store.setStoreName("Super Store");

        SupplyBatchDto supplyBatch = new SupplyBatchDto();
        supplyBatch.setProducts(products);
        supplyBatch.setUuid("UUD1234");
        supplyBatch.setDate(new Date());
        supplyBatch.setStore(store);
        supplyBatch.setOrderId(2332L);

        handleService.handleSupply(supplyBatch);

    }

}