package com.github.hadasbro.warehouse.repository;


import com.github.hadasbro.warehouse.domain.SupplyBatch;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface SupplyBatchRepository extends ReactiveCouchbaseRepository<SupplyBatch, Long> {}
