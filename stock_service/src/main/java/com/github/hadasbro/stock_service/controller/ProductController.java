package com.github.hadasbro.stock_service.controller;

import com.github.hadasbro.stock_service.model.Product;
import com.github.hadasbro.stock_service.service.ProductsService;
import com.github.hadasbro.stock_service.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * ProductController
 */
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(
        path = "/products",
        produces = "application/json",
        consumes = "application/json"
)
public class ProductController {

    @Autowired
    private ApploggerService apploggerService;

    @Autowired
    private ProductsService productsService;

    /**
     * getAllProducts
     *
     * @return Flux<Product>
     */
    @GetMapping("/all")
    public Flux<Product> getAllProducts() {
        return productsService.findAll();
    }

    /**
     * getOneProduct
     *
     * @param uuid -
     * @return Mono<Product>
     */
    @GetMapping("/{uuid}")
    public Mono<Product> getOneProduct(@PathVariable("uuid") UUID uuid) {
        return productsService.findById(uuid);
    }

    /**
     * updateProduct
     * @param uuid -
     * @param product -
     */
    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("uuid") UUID uuid, @RequestBody Product product) {

        if (!product.getId().equals(uuid)) {
            IllegalStateException exc = new IllegalStateException("Product not found.");
            apploggerService.log(exc);
            throw exc;
        }

        productsService.updateProduct(product);
    }

    /**
     * addProduct
     *
     * @param product -
     * @return Mono<Product>
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> addProduct(@RequestBody Product product) {
        return productsService.addProduct(product);
    }

    /**
     * deleteProduct
     *
     * @param id -
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") UUID id) {
        productsService.deleteProduct(id);
    }

}
