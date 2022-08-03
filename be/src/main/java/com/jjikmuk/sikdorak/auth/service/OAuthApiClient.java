package com.jjikmuk.sikdorak.auth.service;

import com.jjikmuk.sikdorak.auth.controller.dto.response.KakaoAccountResponse;
import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientHeaderConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="api-client", url = "https://kapi.kakao.com/", configuration = {FeignClientHeaderConfiguration.class})
public interface OAuthApiClient {

    @GetMapping("/v2/user/me")
    KakaoAccountResponse getUserInfo(@RequestHeader("Authorization") String accessToken);
}
