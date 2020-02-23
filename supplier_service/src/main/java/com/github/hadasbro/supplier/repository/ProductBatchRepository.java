package com.github.hadasbro.supplier.repository;

import com.github.hadasbro.supplier.model.ProductBatch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductBatchRepository extends MongoRepository<ProductBatch, String> {}