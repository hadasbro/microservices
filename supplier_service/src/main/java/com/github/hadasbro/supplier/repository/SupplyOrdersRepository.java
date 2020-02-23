package com.github.hadasbro.supplier.repository;

import com.github.hadasbro.supplier.model.SupplyOrderBatch;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@SuppressWarnings("unused")
public interface SupplyOrdersRepository extends MongoRepository<SupplyOrderBatch, String> {
    List<SupplyOrderBatch> findTop1ByOrderByIdDesc();
}