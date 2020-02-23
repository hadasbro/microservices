package com.github.hadasbro.supplier.repository;

import com.github.hadasbro.supplier.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StoreRepository extends MongoRepository<Store, String> {
    Optional<Store> findOneByStoreCatalogId(String storeCatalogId);
}