package com.github.hadasbro.stock_service.repository;

import com.github.hadasbro.stock_service.model.SupplyBatch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SupplyBatchRepository extends ReactiveCrudRepository<SupplyBatch, String> {}
