package com.github.hadasbro.email_order_service.config;

import com.github.hadasbro.email_order_service.handlers.EmailOrderHandler;
import com.github.hadasbro.email_order_service.transformers.EmailToOrderTransformer;
import com.github.hadasbro.email_order_service.filters.EmailIsAllowedFilter;
import com.github.hadasbro.email_order_service.handlers.IpDisallowedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.ImapMailReceiver;
import org.springframework.integration.mail.dsl.Mail;

import javax.mail.Message;
import java.util.Properties;

@SuppressWarnings("unused")
@Configuration
public class IntegrationConfig {

    /**
     * emailOrderFlow
     *
     * @param emailConfig -
     * @param emailToOrderTransformer -
     * @param emailIsAllowedFilter -
     * @param orderSubmitHandler -
     * @param ipDisallowedHandler -
     * @return IntegrationFlow
     */
    @Bean
    public IntegrationFlow emailOrderFlow(
            EmailConfig emailConfig,
            EmailToOrderTransformer emailToOrderTransformer,
            EmailIsAllowedFilter emailIsAllowedFilter,
            EmailOrderHandler orderSubmitHandler,
            IpDisallowedHandler ipDisallowedHandler
    ) {

        ImapMailReceiver imapMailReceiver = new ImapMailReceiver(emailConfig.getInboxUrl());
        imapMailReceiver.setSimpleContent(true);

        if (emailConfig.isDebug()) {
            Properties prop = new Properties();
            prop.setProperty("mail.debug", "true");
            imapMailReceiver.setJavaMailProperties(prop);
        }

        return IntegrationFlows

                // channel adaptor
                .from(
                        Mail.imapInboundAdapter(imapMailReceiver),
                        config -> config.poller(
                                Pollers
                                    .fixedDelay(emailConfig.getDelay())
                                    .maxMessagesPerPoll(emailConfig.getMaxMessages())
                                    .sendTimeout(emailConfig.getTimout())
                        )
                )

                // filter only allowed emails (allowed and registered clients)
                .filter(
                        Message.class,
                        emailIsAllowedFilter,
                        endpointSpec -> endpointSpec.discardFlow(dFlow -> {
                            dFlow.handle(ipDisallowedHandler);
                        })
                )

                // transform message to the Order
                .transform(emailToOrderTransformer)

                // handle, service activator
                .handle(orderSubmitHandler)

                // get
                .get();
    }

}