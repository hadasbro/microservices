package com.github.hadasbro.stock_service.controller;

import com.github.hadasbro.stock_service.model.Order;
import com.github.hadasbro.stock_service.service.EmailOrderService;
import com.github.hadasbro.stock_service.service.OrderService;
import com.github.hadasbro.stock_service.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * OrderController
 */
@SuppressWarnings("unused")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(
        path = "/orders",
        produces = "application/json",
        consumes = "application/json"
)
public class OrderController {

    @Autowired
    private ApploggerService apploggerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailOrderService emailOrderService;

    /**
     * getAllOrders
     *
     * @return Flux<Order> -
     */
    @GetMapping("/all")
    public Flux<Order> getAllOrders() {
        return orderService.getAll();
    }

    /**
     * addOrder
     *
     * @param order -
     * @return Mono<Order>
     */
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Order> addOrder(@RequestBody Order order) {
        return this.orderService.addOrder(order);
    }

    /**
     * updateOrder
     * @param uuid -
     * @param order -
     */
    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateOrder(@PathVariable("uuid") UUID uuid, @RequestBody Order order) {
        this.orderService.updateOrder(uuid, order);
    }

    /**
     * patchOrder
     *
     * @param uuid -
     * @param order -
     * @return Mono<Order>
     */
    @PatchMapping("/{uuid}")
    public Mono<Order> patchOrder(@PathVariable("uuid") UUID uuid, @RequestBody Order order) {
        return this.orderService.patchOrder(uuid, order);
    }

    /**
     * deleteOrder
     *
     * @param uuid -
     */
    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("uuid") UUID uuid) {
        try {
            orderService.deleteOrder(uuid);
        } catch (EmptyResultDataAccessException exc) {
            apploggerService.log(exc);
        }
    }

}
