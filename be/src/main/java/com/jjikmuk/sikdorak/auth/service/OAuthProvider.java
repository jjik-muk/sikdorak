package com.jjikmuk.sikdorak.auth.service;

public interface OAuthProvider {

    String getLoginPageUrl();

    String getAccessToken();

    String getUserInfo();
}
