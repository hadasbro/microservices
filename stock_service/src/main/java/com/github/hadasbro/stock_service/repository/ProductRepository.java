package com.github.hadasbro.stock_service.repository;

import com.github.hadasbro.stock_service.model.Product;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.UUID;

public interface ProductRepository extends ReactiveCrudRepository<Product, UUID> {

    @AllowFiltering
    Flux<Product> findByCatalogIdIn(Collection<String> catalogId);

}
