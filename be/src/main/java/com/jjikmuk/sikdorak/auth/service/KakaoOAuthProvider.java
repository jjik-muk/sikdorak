package com.jjikmuk.sikdorak.auth.service;

import com.jjikmuk.sikdorak.common.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoOAuthProvider implements OAuthProvider {

    private static final String LOGIN_PAGE_URL = "https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=%s";
    private final KakaoProperties kakaoProperties;

    @Override
    public String getLoginPageUrl() {
        return String.format(LOGIN_PAGE_URL, kakaoProperties.getClientId(), kakaoProperties.getRedirectUri(), kakaoProperties.getResponseType());
    }

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public String getUserInfo() {
        return null;
    }
}
