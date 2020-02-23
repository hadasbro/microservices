package com.github.hadasbro.stock_service.controller;

import com.github.hadasbro.stock_service.model.SupplyOrderBatch;
import com.github.hadasbro.stock_service.service.SupplyOrderService;
import com.github.hadasbro.stock_service.udt.ProductBatchUdt;
import com.github.hadasbro.stock_service.udt.StoreUdt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * TestController
 */
@SuppressWarnings("unused")
@Profile("test")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/supply-orders")
public class TestController {

    @Autowired
    private SupplyOrderService supplyOrderService;

    /**
     * TEST service
     *
     * sendSupplyOrder
     *
     * method sends Supply Order (SupplyOrderBatchDto) to service "produc_supplier"
     * POST new SupplyOrderBatchDto() -> http://_HOST_:6062/supply/order
     */
    @PostMapping("/test")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendSupplyOrderTest() {

        List<ProductBatchUdt> products = new ArrayList<>();

        ProductBatchUdt pbatch = new ProductBatchUdt();
        pbatch.setCatalogId("product-catalog-id3");
        pbatch.setQuantity(5);
        products.add(pbatch);

        SupplyOrderBatch sbatch = new SupplyOrderBatch();
        sbatch.setProducts(products);
        sbatch.setDate(new Date());
        sbatch.setStatus(SupplyOrderBatch.STATUS.NONE);

        StoreUdt storeUdt = new StoreUdt();
        storeUdt.setAddress("address");
        storeUdt.setPhone("111-222-333");
        storeUdt.setStoreCatalogId("store1");
        storeUdt.setStoreName("Name Store");

        sbatch.setStore(storeUdt);

        supplyOrderService.orderSupply(sbatch);

    }

}
