package com.jjikmuk.sikdorak.common.config.feignclient;

import com.jjikmuk.sikdorak.common.util.feignclient.FeignClientResponseUtils;
import com.jjikmuk.sikdorak.store.exception.PlaceApiServerException;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;

@Slf4j
public class FeignClientKakaoApiErrorConfiguration {

    @Bean
    public ErrorDecoder decoder() {
        return (methodKey, response) -> {
            if (log.isErrorEnabled()) {
                log.error("{} 요청이 성공하지 못했습니다. requestUrl: {}, requestBody: {}, responseBody: {}",
                    methodKey,
                    response.request().url(),
                    FeignClientResponseUtils.getRequestBody(response),
                    FeignClientResponseUtils.getResponseBody(response));
            }
            return new PlaceApiServerException();
        };
    }

}
