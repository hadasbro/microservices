package com.github.hadasbro.email_order_service.filters;

import com.github.hadasbro.email_order_service.services.graylog.ApploggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.core.GenericSelector;
import org.springframework.stereotype.Component;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

@SuppressWarnings("unused")
@Component
public class EmailIsAllowedFilter implements GenericSelector<Message> {

    @Autowired
    private ApploggerService apploggerService;

    /**
     * accept
     *
     * Filter/process only emails from clients and
     * allowed to send and oreder vie email
     *
     * @param mailMessage -
     * @return boolean -
     */
    @Override
    public boolean accept(Message mailMessage) {

        Address[] froms;

        try {
            froms = mailMessage.getFrom();
        } catch (MessagingException e) {
            apploggerService.log(e);
            return false;
        }

        String senderEmail = froms == null ? null : ((InternetAddress) froms[0]).getAddress();

        if (isEmailOnBlacklist(senderEmail)) {
            return false;
        }

        return !isAlreadyProcessed(mailMessage);
    }

    /**
     * isEmailOnBlacklist
     *
     * @param email -
     * @return boolean
     */
    private boolean isEmailOnBlacklist(String email) {
        return false;
    }

    /**
     * isAlreadyProcessed
     *
     * @param mailMessage -
     * @return boolean -
     */
    private boolean isAlreadyProcessed(Message mailMessage) {
        return false;
    }

}