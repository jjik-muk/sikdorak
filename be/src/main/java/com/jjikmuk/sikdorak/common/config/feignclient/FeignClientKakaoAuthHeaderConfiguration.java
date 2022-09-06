package com.jjikmuk.sikdorak.common.config.feignclient;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class FeignClientKakaoAuthHeaderConfiguration {

    @Value("${api.kakao.token}")
    private String apiToken;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("Authorization", apiToken);
    }
}
