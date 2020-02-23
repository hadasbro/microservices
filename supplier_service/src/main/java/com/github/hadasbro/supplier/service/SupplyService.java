package com.github.hadasbro.supplier.service;

import com.github.hadasbro.supplier.dto.supply.SupplyBatchDto;
import com.github.hadasbro.supplier.service.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@SuppressWarnings("unused")
@Service
public class SupplyService {

    /**
     * topic
     */
    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    /**
     * kafkaTemplate
     */
    private KafkaTemplate<String, SupplyBatchDto> kafkaTemplate;

    /**
     * SupplyService
     *
     * @param kafkaTemplate -
     */
    @Autowired
    public SupplyService(KafkaTemplate<String, SupplyBatchDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Autowired
    private ApploggerService apploggerService;

    /**
     * provideBatch
     * <p>
     * KAFKA sender
     *
     * @param supplyBatchDto -
     */
    public void provideBatch(SupplyBatchDto supplyBatchDto) {
        try {
            kafkaTemplate.send(topic, supplyBatchDto);
        } catch (Throwable t) {
            apploggerService.log(t);
        }
    }

}
