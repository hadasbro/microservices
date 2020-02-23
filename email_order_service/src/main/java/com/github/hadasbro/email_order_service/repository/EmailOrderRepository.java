package com.github.hadasbro.email_order_service.repository;

import com.github.hadasbro.email_order_service.domain.EmailOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailOrderRepository extends CrudRepository<EmailOrder, Long> {}