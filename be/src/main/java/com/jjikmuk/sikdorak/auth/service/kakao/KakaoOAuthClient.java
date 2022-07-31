package com.jjikmuk.sikdorak.auth.service.kakao;

import com.jjikmuk.sikdorak.auth.controller.dto.response.OAuthTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="KakaoClient", url = "https://kauth.kakao.com/")
public interface KakaoOAuthClient {

    @PostMapping("/oauth/token")
    OAuthTokenResponse getAccessToken(@RequestParam(value = "grant_type") String grantType,
                                      @RequestParam(value = "client_id") String clientId,
                                      @RequestParam(value = "redirect_uri") String redirectUri,
                                      @RequestParam(value = "code") String code);
}
