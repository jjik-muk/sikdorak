package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientHeaderConfiguration;
import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientOAuthErrorConfiguration;
import com.jjikmuk.sikdorak.user.auth.app.response.KakaoAccountResponse;
import com.jjikmuk.sikdorak.user.auth.app.response.OAuthTokenResponse;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "token-client", configuration = {
    FeignClientHeaderConfiguration.class,
    FeignClientOAuthErrorConfiguration.class})
public interface OAuthClient {

    @PostMapping
    OAuthTokenResponse getAccessToken(URI tokenUrl,
        @RequestParam(value = "grant_type") String grantType,
        @RequestParam(value = "client_id") String clientId,
        @RequestParam(value = "client_secret") String clientSecret,
        @RequestParam(value = "redirect_uri") String redirectUri,
        @RequestParam(value = "code") String code);

    @GetMapping
    KakaoAccountResponse getUserInfo(@RequestHeader("Authorization") String accessToken);
}
