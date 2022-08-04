package com.jjikmuk.sikdorak.auth.service;

import com.jjikmuk.sikdorak.auth.controller.response.OAuthTokenResponse;
import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientHeaderConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="token-client", url = "https://kauth.kakao.com/", configuration = {FeignClientHeaderConfiguration.class})
public interface OAuthTokenClient {

    @PostMapping("/oauth/token")
    OAuthTokenResponse getAccessToken(@RequestParam(value = "grant_type") String grantType,
                                      @RequestParam(value = "client_id") String clientId,
                                      @RequestParam(value = "redirect_uri") String redirectUri,
                                      @RequestParam(value = "code") String code);
}
