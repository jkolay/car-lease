package com.carlease.lease.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Customer Client Property Model
 */
@Component
@ConfigurationProperties("customer.client")
@Data
public class CustomerClientProperties {
    private String hostname;
    private String baseUrl;
    private  Integer port;
}
