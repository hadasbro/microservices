package com.github.hadasbro.graylog_service.service.graylog.client.sample;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SuppressWarnings({"unused", "WeakerAccess"})
@Profile("sample")
@EnableJms
@Configuration
@ConfigurationProperties(prefix="spring.activemq")
class ApploggerConfig {

    private String destination;

    /**
     * queueListenerFactory
     *
     * @return JmsListenerContainerFactory<?>
     */
    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    /**
     * messageConverter
     *
     * @return MessageConverter
     */
    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    /**
     * getDestination
     *
     * @return String
     */
    public String getDestination() {
        return destination;
    }

    /**
     * setDestination
     *
     * @param destination -
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

}