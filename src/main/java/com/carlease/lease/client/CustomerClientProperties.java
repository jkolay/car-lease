package com.carlease.lease.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("customer.client")
@Data
public class CustomerClientProperties {
    private String hostname;
    private String baseUrl;
}
