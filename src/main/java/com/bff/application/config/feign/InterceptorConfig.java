package com.bff.application.config.feign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InterceptorConfig {

    @Value("${integration.product-service.token}")
    private String authToken;

    @Bean
    public RequestInterceptor feignAuthInterceptor() {
        return requestTemplate -> requestTemplate.header(
                ClientHeaders.AUTHORIZATION,
                ClientHeaders.BEARER_PREFIX + authToken
        );
    }

}
