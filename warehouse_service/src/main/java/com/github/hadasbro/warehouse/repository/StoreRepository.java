package com.github.hadasbro.warehouse.repository;

import com.github.hadasbro.warehouse.domain.Store;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;

public interface StoreRepository extends ReactiveCouchbaseRepository<Store, String> {}
