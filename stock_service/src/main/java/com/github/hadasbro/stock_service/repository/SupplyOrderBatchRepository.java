package com.github.hadasbro.stock_service.repository;

import com.github.hadasbro.stock_service.model.SupplyOrderBatch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SupplyOrderBatchRepository extends ReactiveCrudRepository<SupplyOrderBatch, String> {}
