package com.activity.activity.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }


    @Bean
    public WebClient userServiceWebClient(
            @LoadBalanced WebClient.Builder webClientBuilder) {

        return webClientBuilder
                .baseUrl("http://userservice")
                .build();
    }
}
