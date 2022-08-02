package com.jjikmuk.sikdorak.common.config.feignclient;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class FeignClientHeaderConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    }

}
