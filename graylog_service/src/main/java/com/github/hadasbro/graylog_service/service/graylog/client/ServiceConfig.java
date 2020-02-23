package com.github.hadasbro.graylog_service.service.graylog.client;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

@SuppressWarnings({"unused", "WeakerAccess"})
@EnableJms
@Configuration
@ConfigurationProperties(prefix = "spring.activemq")
class ServiceConfig {

    /**
     * destination
     */
    private String destination;

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    /**
     * receiverActiveMQConnectionFactory
     *
     * @return ActiveMQConnectionFactory
     */
    @Bean
    public ActiveMQConnectionFactory receiverActiveMQConnectionFactory() {
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
        activeMQConnectionFactory.setBrokerURL(brokerUrl);
        return activeMQConnectionFactory;
    }

    /**
     * jmsListenerContainerFactory
     *
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(receiverActiveMQConnectionFactory());
        return factory;
    }

    /**
     * receiver
     *
     * @return ActiveMqComponent
     */
    @Bean
    public ActiveMqComponent receiver() {
        return new ActiveMqComponent();
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

    /**
     * getBrokerUrl
     * @return String
     */
    public String getBrokerUrl() {
        return brokerUrl;
    }

    /**
     * setBrokerUrl
     *
     * @param brokerUrl -
     */
    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }
}