package com.github.hadasbro.warehouse.component;

import com.github.hadasbro.warehouse.dto.SupplyBatchDto;
import com.github.hadasbro.warehouse.service.SupplyHandleService;
import com.github.hadasbro.warehouse.service.graylog.ApploggerService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * SupplyListener
 */
@SuppressWarnings("unused")
@Component
public class SupplyListener {

    @Autowired
    private SupplyHandleService handleService;

    @Autowired
    private ApploggerService apploggerService;

    @Autowired
    public SupplyListener(SupplyHandleService handleService) {
        this.handleService = handleService;
    }

    /**
     * handle
     *
     * @param supplyBatch -
     * @param record -
     */
    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
    public void handle(SupplyBatchDto supplyBatch, ConsumerRecord<String, SupplyBatchDto> record) {

        try {
            handleService.handleSupply(supplyBatch);
        } catch (Throwable t) {
            apploggerService.log(t);
        }

    }

}
