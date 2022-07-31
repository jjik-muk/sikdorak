package com.jjikmuk.sikdorak.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuthService{
    private final OAuthProvider oAuthProvider;

    public String getLoginPageUrl() {
        return oAuthProvider.getLoginPageUrl();
    }
}
