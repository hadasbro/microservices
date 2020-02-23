package com.github.hadasbro.graylog_service.service.graylog.client;

import com.github.hadasbro.graylog_service.dto.GrayLogException;
import com.github.hadasbro.graylog_service.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Session;

@SuppressWarnings("unused")
@Component
public class ActiveMqComponent {

    @Autowired
    private LogService logService;

    /**
     * receiveMessage
     *
     * @param glException -
     * @param headers -
     * @param message -
     * @param session -
     */
    @JmsListener(destination = "${spring.activemq.destination}")
    public void receiveMessage(
            @Payload GrayLogException glException,
            @Headers MessageHeaders headers,
            Message message,
            Session session
    ) {
        logService.process(glException);
    }

}