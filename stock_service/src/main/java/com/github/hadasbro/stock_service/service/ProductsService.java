package com.github.hadasbro.stock_service.service;

import com.github.hadasbro.stock_service.model.Product;
import com.github.hadasbro.stock_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * ProductsService
 */
@Service
@SuppressWarnings("unused")
public class ProductsService {

    @Autowired
    private ProductRepository repository;

    /**
     * findAll
     *
     * @return Flux<Product> -
     */
    public Flux<Product> findAll() {
        return repository.findAll();
    }

    /**
     * findById
     *
     * @param uuid -
     * @return Mono<Product> -
     */
    public Mono<Product> findById(UUID uuid) {
        return repository.findById(uuid);
    }

    /**
     * updateProduct
     *
     * @param product -
     */
    public void updateProduct(Product product) {
        repository.save(product);
    }

    /**
     * addProduct
     *
     * @param product -
     * @return Mono<Product> -
     */
    public Mono<Product> addProduct(Product product) {
        return repository.save(product);
    }

    /**
     * deleteProduct
     *
     * @param id -
     */
    public void deleteProduct(UUID id) {
        repository.deleteById(id);
    }
}




