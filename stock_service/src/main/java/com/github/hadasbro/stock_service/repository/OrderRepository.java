package com.github.hadasbro.stock_service.repository;

import com.github.hadasbro.stock_service.model.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface OrderRepository extends ReactiveCrudRepository<Order, UUID> {}