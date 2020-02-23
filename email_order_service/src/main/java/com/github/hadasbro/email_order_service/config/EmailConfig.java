package com.github.hadasbro.email_order_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "email")
class EmailConfig {

    private String username;
    private String password;
    private String host;
    private String mailbox;
    private int port;
    private long delay = 10000;
    private int maxMessages = 1000;
    private int timout = 30;
    private boolean debug = false;


    /**
     * getInboxUrl
     * @return String
     */
    String getInboxUrl() {

        return String.format(
                "imaps://%s:%s@%s:%s/%s",
                this.username,
                this.password,
                this.host,
                this.port,
                this.mailbox
        );
    }

}