package com.bff.application.client.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {

    @Value("${integration.product-service.token}")
    private String authToken;

    @Bean
    public RequestInterceptor feignAuthInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", "Bearer " + authToken);
    }

}
