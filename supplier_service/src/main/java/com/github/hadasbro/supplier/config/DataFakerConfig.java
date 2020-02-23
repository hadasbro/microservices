package com.github.hadasbro.supplier.config;

import com.github.hadasbro.supplier.model.ProductBatch;
import com.github.hadasbro.supplier.model.Store;
import com.github.hadasbro.supplier.model.SupplyOrderBatch;
import com.github.hadasbro.supplier.repository.ProductBatchRepository;
import com.github.hadasbro.supplier.repository.StoreRepository;
import com.github.hadasbro.supplier.repository.SupplyOrdersRepository;
import com.github.hadasbro.supplier.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * DataFakerConfig
 *
 * only for profile=fakedata
 */
@SuppressWarnings("unused")
@Profile("test")
@Configuration
public class DataFakerConfig {

    @Autowired
    private ApploggerService logService;

    @Autowired
    private ProductBatchRepository productBatchRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private SupplyOrdersRepository supplyOrdersRepository;

    /**
     * dataLoader
     *
     * @return CommandLineRunner
     */
    @Bean
    public CommandLineRunner dataLoader() {
        return args -> loadFakeData();
    }

    /**
     * loadFakeData
     */
    private void loadFakeData() {

        try {

            int storesCount = 15;
            int productsCount = 25;
            int ordersCount = 55;

            List<Store> stores = new ArrayList<>();
            List<ProductBatch> products = new ArrayList<>();
            List<SupplyOrderBatch> orders = new ArrayList<>();

            for (int i = 1; i < storesCount; i++) {
                Instant instant = Instant.now();
                long timeStampMillis = instant.toEpochMilli();
                Store store = new Store();
                store.setAddress("Store Address [ " + i + " ]");
                store.setPhone("000-111-222");
                store.setStoreCatalogId("store-" + i + "-" + timeStampMillis);
                store.setStoreName("Store" + i);
                stores.add(store);
                store.setStoreId((long) i);
            }

            storeRepository.saveAll(stores);

            for (int i = 1; i < ordersCount; i++) {

                List<ProductBatch> pbatch = new ArrayList<>();

                for (int ii = 2 * i; ii < 2 * i + 5; ii++) {
                    ProductBatch productBatch = new ProductBatch();
                    productBatch.setCatalogId("product-catalog-id" + ii);
                    productBatch.setQuantity(5);
                    pbatch.add(productBatch);
                }

                SupplyOrderBatch supplyOrderBatch = new SupplyOrderBatch();
                supplyOrderBatch.setDate(new Date());
                int ind = (i % (storesCount - 2) + 1);
                supplyOrderBatch.setStore(stores.get(ind > 0 ? ind : 1));
                supplyOrderBatch.setProducts(pbatch);
                supplyOrderBatch.setUuid(i + "uuid");

                if (i % 5 == 0) {
                    supplyOrderBatch.setStatus(SupplyOrderBatch.STATUS.ORDERED);
                } else if (i % 3 == 1) {
                    supplyOrderBatch.setStatus(SupplyOrderBatch.STATUS.PROVIDED);
                } else {
                    supplyOrderBatch.setStatus(SupplyOrderBatch.STATUS.READY_TO_SEND);
                }

                orders.add(supplyOrderBatch);

            }

            supplyOrdersRepository.saveAll(orders);

        } catch (Throwable t) {
            logService.log(t);
        }
    }

}
