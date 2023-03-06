package com.carlease.lease.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class CarLeaseClientConfig {

        @Bean(name = "RestTemplate")
        @LoadBalanced
        public RestTemplate restTemplate(RestTemplateBuilder builder,
                                         @Value("${restTemplate.timeout.connectSeconds}") int connectionTimeoutSeconds,
                                         @Value("${restTemplate.timeout.readSeconds}") int readTimeoutSeconds) {
            return builder.setConnectTimeout(Duration.ofMillis(connectionTimeoutSeconds * 1000))
                    .setReadTimeout(Duration.ofMillis(readTimeoutSeconds * 1000))
                    .build();
        }

    }
