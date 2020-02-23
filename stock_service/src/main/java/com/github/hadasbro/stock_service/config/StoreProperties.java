package com.github.hadasbro.stock_service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotEmpty;

/**
 * StoreProperties
 */
@ConfigurationProperties(prefix = "store")
@Getter @Setter
public class StoreProperties {

    @NotEmpty
    private String catalog_id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phone;

}