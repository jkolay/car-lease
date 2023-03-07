package com.carlease.lease.client;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("cars.client")
@Data
public class CarClientProperties {
        private String hostname;
        private String baseUrl;
    }