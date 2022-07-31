package com.jjikmuk.sikdorak.auth.service;

import com.jjikmuk.sikdorak.auth.controller.dto.response.JwtTokenResponse;

public interface OAuthService {

    String getLoginPageUrl();

    JwtTokenResponse login(String code);

    default void createToken() {

    }
}
