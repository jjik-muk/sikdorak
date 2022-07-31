package com.jjikmuk.sikdorak.common.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth.kakao")
public class KakaoProperties {

    private final String clientId;
    private final String redirectUri;
    private final String responseType;

    public KakaoProperties(String clientId, String redirectUri, String responseType) {
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
    }
}
