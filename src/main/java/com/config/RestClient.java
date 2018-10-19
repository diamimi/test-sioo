package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: HeQi
 * @Date:Create in 10:27 2018/10/17
 */
@Configuration
public class RestClient {

    @Bean
    public RestTemplate getRestTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        return restTemplate;
    }
}
