package com.jjikmuk.sikdorak.common.config.feignclient;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class FeignClientHeaderConfiguration {

    public static final String APPLICATION_FORM_URLENCODED_UTF8_VALUE =
        MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=utf-8";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(HttpHeaders.CONTENT_TYPE, APPLICATION_FORM_URLENCODED_UTF8_VALUE);
    }

}
