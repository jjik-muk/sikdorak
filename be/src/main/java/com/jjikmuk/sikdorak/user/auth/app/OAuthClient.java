package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientOAuthHeaderConfiguration;
import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientOAuthErrorConfiguration;
import com.jjikmuk.sikdorak.user.auth.app.response.OAuthTokenResponse;
import com.jjikmuk.sikdorak.user.auth.app.response.OAuthUserResponse;
import java.net.URI;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "token-client", url = "https://placeholder-url",configuration = {
    FeignClientOAuthHeaderConfiguration.class,
    FeignClientOAuthErrorConfiguration.class})
public interface OAuthClient {

    @PostMapping
    OAuthTokenResponse getAccessToken(URI tokenUrl, @RequestBody MultiValueMap<String, String> body);


    @GetMapping
    OAuthUserResponse getUserInfo(URI userInfoUrl, @RequestHeader("Authorization") String accessToken);
}
