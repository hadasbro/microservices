package com.github.hadasbro.email_order_service.handlers;

import com.github.hadasbro.email_order_service.services.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.DiscardingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * IpDisallowedHandler
 */
@SuppressWarnings("unused")
@Component
public class IpDisallowedHandler implements DiscardingMessageHandler {

    @Autowired
    private ApploggerService apploggerService;

    @Override
    public MessageChannel getDiscardChannel() {
        return null;
    }

    /**
     * void
     *
     * @param message -
     * @throws MessagingException -
     */
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {

        try {

            MimeMessage mimeMessage = ((MimeMessage) message.getPayload());
            Address[] froms = mimeMessage.getFrom();
            String senderIP = froms == null ? null : ((InternetAddress) froms[0]).getAddress();

            // TODO

        } catch (javax.mail.MessagingException e) {
            apploggerService.log(e);
        }

    }
}
