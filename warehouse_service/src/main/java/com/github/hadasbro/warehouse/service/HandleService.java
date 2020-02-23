package com.github.hadasbro.warehouse.service;

import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.error.DocumentAlreadyExistsException;
import com.couchbase.client.java.error.DocumentDoesNotExistException;
import com.couchbase.client.java.error.QueryExecutionException;
import com.github.hadasbro.warehouse.domain.Store;
import com.github.hadasbro.warehouse.domain.SupplyBatch;
import com.github.hadasbro.warehouse.repository.StoreRepository;
import com.github.hadasbro.warehouse.repository.SupplyBatchRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SuppressWarnings({"unused", "WeakerAccess"})
@Service
public class HandleService {

    /**
     * StoreRepository
     */
    private final StoreRepository storeRepository;

    /**
     * SupplyBatchRepository
     */
    private final SupplyBatchRepository supplyBatchRepository;

    /**
     * HandleService
     *
     * @param storeRepository -
     * @param supplyBatchRepository -
     */
    public HandleService(StoreRepository storeRepository, SupplyBatchRepository supplyBatchRepository) {
        this.storeRepository = storeRepository;
        this.supplyBatchRepository = supplyBatchRepository;
    }

    /**
     * findAllSupplies
     *
     * @return Flux<SupplyBatch> -
     */
    public Flux<SupplyBatch> findAllSupplies() {
        return supplyBatchRepository.findAll();
    }

    /**
     * upsertSupply
     *
     * @param supplyBatch -
     * @return Mono<SupplyBatch> -
     */
    public Mono<SupplyBatch> upsertSupply(SupplyBatch supplyBatch) {
        return supplyBatchRepository.save(supplyBatch);
    }

    /**
     * insertSupply
     *
     * @param supplyBatch -
     * @return Mono<SupplyBatch> -
     */
    public Mono<SupplyBatch> insertSupply(SupplyBatch supplyBatch) {
        return supplyBatchRepository.existsById(supplyBatch.getOrderId())
                .flatMap(
                        exists -> exists
                                ? error(supplyBatch.getOrderId(), SupplyBatch.class, DocumentAlreadyExistsException.class)
                                : supplyBatchRepository.save(supplyBatch));
    }

    /**
     * updateSupply
     *
     * @param id -
     * @param supplyBatch -
     * @return Mono<SupplyBatch> -
     */
    public Mono<SupplyBatch> updateSupply(Long id, SupplyBatch supplyBatch) {
        return supplyBatchRepository
                .existsById(id)
                .flatMap(
                        exists -> exists
                                ? supplyBatchRepository.save(supplyBatch)
                                : error(id, SupplyBatch.class, DocumentDoesNotExistException.class)
                );
    }

    /**
     * deleteSupply
     *
     * @param id -
     * @return Mono<Void>
     */
    public Mono<Void> deleteSupply(Long id) {
        return supplyBatchRepository.existsById(id)
                .flatMap(exists -> exists
                        ? supplyBatchRepository.deleteById(id)
                        : error(id, SupplyBatch.class, DocumentAlreadyExistsException.class)
                );

    }

    /**
     * findSupply
     *
     * @param id -
     * @return Mono<SupplyBatch>
     */
    public Mono<SupplyBatch> findSupply(Long id) {
        return supplyBatchRepository.findById(id)
                .switchIfEmpty(error(id, SupplyBatch.class, DocumentDoesNotExistException.class));
    }

    /**
     * findAllStores
     *
     * @return Flux<Store> -
     */
    public Flux<Store> findAllStores() {
        return storeRepository.findAll();
    }

    /**
     * upsertStore
     *
     * @param store -
     * @return Mono<Store> -
     */
    public Mono<Store> upsertStore(Store store) {
        return storeRepository.save(store);
    }

    /**
     * insertStore
     *
     * @param store -
     * @return Mono<Store> -
     */
    public Mono<Store> insertStore(Store store) {
       return storeRepository.existsById(store.getCatalogId())
               .flatMap(
                       exists -> exists
                               ? error(store.getCatalogId(), Store.class, DocumentAlreadyExistsException.class)
                               : storeRepository.save(store));
    }

    /**
     * updateStore
     *
     * @param catalogId -
     * @param store -
     * @return Mono<Store>
     */
    public Mono<Store> updateStore(String catalogId, Store store) {
        return storeRepository
                .existsById(catalogId)
                .flatMap(
                        exists -> exists
                                ? storeRepository.save(store)
                                : error(catalogId, Store.class, DocumentDoesNotExistException.class)
                );
    }

    /**
     * deleteStore
     *
     * @param catalogId -
     * @return Mono<Void> -
     */
    public Mono<Void> deleteStore(String catalogId) {
        return storeRepository.existsById(catalogId)
                .flatMap(exists -> exists
                        ? storeRepository.deleteById(catalogId)
                        : error(catalogId, Store.class, DocumentAlreadyExistsException.class)
                );

    }

    /**
     * findStore
     *
     * @param catalogId -
     * @return Mono<Store> -
     */
    public Mono<Store> findStore(String catalogId) {
        return storeRepository.findById(catalogId)
                .switchIfEmpty(error(catalogId, Store.class, DocumentDoesNotExistException.class));
    }

    /**
     * error
     *
     * @param id -
     * @param oject -
     * @param type -
     * @param <T> -
     * @return Mono<T> -
     */
    private <T> Mono<T> error(Object id, Class<?> oject, Class<?> type) {

        if (type ==  DocumentDoesNotExistException.class) {
            return Mono.error(new DocumentDoesNotExistException(
                    String.format("%s #%s does not exist", oject, id))
            );
        }

        if (type ==  DocumentAlreadyExistsException.class) {
            return Mono.error(new DocumentAlreadyExistsException(
                    String.format("%s #%s exist already", oject, id)
                    ));
        }

        return (Mono<T>) Mono.just(
                new QueryExecutionException(
                        String.format("%s #%s euqry exception", oject, id), JsonObject.empty()
                )
        );
    }

}
