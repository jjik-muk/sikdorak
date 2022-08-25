package com.jjikmuk.sikdorak.common.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoProperties {

    @NotEmpty
    private final String loginPageUrl;
    @NotEmpty
    private final String grantType;
    @NotEmpty
    private final String clientId;
    @NotEmpty
    private final String redirectUri;
    @NotEmpty
    private final String responseType;

    public KakaoProperties(String loginPageUrl, String grantType, String clientId, String redirectUri, String responseType) {
        this.loginPageUrl = loginPageUrl;
        this.grantType = grantType;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
    }

    public String getLoginPageUrl() {
        return String.format(loginPageUrl, clientId, redirectUri, responseType);
    }
}
