package com.github.hadasbro.stock_service.service;

import com.github.hadasbro.stock_service.model.Order;
import com.github.hadasbro.stock_service.repository.OrderRepository;
import com.github.hadasbro.stock_service.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.UUID;

/**
 * OrderService
 */
@Service
@SuppressWarnings("unused")
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ApploggerService apploggerService;

    /**
     * getAll
     *
     * @return Flux<Order> -
     */
    public Flux<Order> getAll() {
        return repository.findAll();
    }

    /**
     * addOrder
     *
     * @param order -
     * @return Mono<Order> -
     */
    public Mono<Order> addOrder(Order order) {
        return repository.save(order);
    }

    /**
     * updateOrder
     *
     * @param uuid -
     * @param order -
     */
    public void updateOrder(UUID uuid, Order order) {

        Mono<Order> mOrder = repository.findById(uuid);

        mOrder.map(ord -> {
            ord.setUserUdt(Objects.requireNonNull(order).getUserUdt());
            ord.setType(order.getType());
            ord.setStatus(order.getStatus());
            ord.setPlacedAt(order.getPlacedAt());
            ord.setProductBatchUdts(order.getProductBatchUdts());
            return ord;
        });

        mOrder.flatMap(repository::save);

    }

    /**
     * patchOrder
     *
     * @param uuid -
     * @param order -
     * @return Mono<Order> -
     */
    public Mono<Order> patchOrder(UUID uuid, Order order) {
        return repository.findById(uuid).flatMap(repository::save);
    }

    /**
     * deleteOrder
     *
     * @param uuid -
     */
    public void deleteOrder(UUID uuid) {
        try {
            repository.deleteById(uuid);
        } catch (EmptyResultDataAccessException exc) {
            apploggerService.log(exc);
        }
    }
}




