package com.github.hadasbro.email_order_service.services;

import com.github.hadasbro.email_order_service.domain.EmailOrder;
import com.github.hadasbro.email_order_service.repository.EmailOrderRepository;
import com.github.hadasbro.email_order_service.services.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EmailOrderService
 */
@SuppressWarnings("unused")
@Service
public class EmailOrderService {

    @Autowired
    private ApploggerService apploggerService;

    @Autowired
    private EmailOrderRepository emailOrderRepository;

    /**
     * save
     *
     * @param order -
     */
    public void save(EmailOrder order) {
        try {
            emailOrderRepository.save(order);
        } catch (Throwable t) {
            apploggerService.log(t);
        }
    }

}
