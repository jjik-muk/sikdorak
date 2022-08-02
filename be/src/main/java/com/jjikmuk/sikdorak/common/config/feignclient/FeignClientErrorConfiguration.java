package com.jjikmuk.sikdorak.common.config.feignclient;

import com.jjikmuk.sikdorak.auth.exception.KakaoApiException;
import com.jjikmuk.sikdorak.common.util.feignclient.FeignClientResponseUtils;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FeignClientErrorConfiguration {

    @Bean
    public ErrorDecoder decoder() {

        return (methodKey, response) -> {
            log.error("{} 요청이 성공하지 못했습니다. requestUrl: {}, requestBody: {}, responseBody: {}",
                    methodKey, response.request().url(), FeignClientResponseUtils.getRequestBody(response), FeignClientResponseUtils.getResponseBody(response));

            return new KakaoApiException();
        };
    }
}
