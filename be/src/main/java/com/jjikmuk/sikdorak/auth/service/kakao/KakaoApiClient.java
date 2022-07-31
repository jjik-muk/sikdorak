package com.jjikmuk.sikdorak.auth.service.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="KakaoApiClient", url = "https://kapi.kakao.com/")
public interface KakaoApiClient {

    @GetMapping("/v2/user/me")
    String getUserInfo(@RequestHeader("Authorization") String accessToken);
}
