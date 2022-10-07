package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientHeaderConfiguration;
import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientOAuthErrorConfiguration;
import com.jjikmuk.sikdorak.user.auth.app.response.KakaoAccountResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "api-client", url = "${oauth.kakao.service.api_url}", configuration = {
    FeignClientHeaderConfiguration.class,
    FeignClientOAuthErrorConfiguration.class})
public interface OAuthApiClient {

    @GetMapping("/v2/user/me")
    KakaoAccountResponse getUserInfo(@RequestHeader("Authorization") String accessToken);
}
