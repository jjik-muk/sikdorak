package com.jjikmuk.sikdorak.auth.service.kakao;

import com.jjikmuk.sikdorak.auth.controller.dto.response.UserInfoResponse;
import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientHeaderConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="KakaoApiClient", url = "https://kapi.kakao.com/", configuration = {FeignClientHeaderConfiguration.class})
public interface KakaoApiClient {

    @GetMapping("/v2/user/me")
    UserInfoResponse getUserInfo(@RequestHeader("Authorization") String accessToken);
}
