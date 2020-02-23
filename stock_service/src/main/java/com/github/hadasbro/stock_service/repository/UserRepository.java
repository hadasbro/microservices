package com.github.hadasbro.stock_service.repository;

import com.github.hadasbro.stock_service.model.User;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    @AllowFiltering
    Mono<User> findByUsername(String username);

    @AllowFiltering
    Mono<User> findByEmail(String email);

}
