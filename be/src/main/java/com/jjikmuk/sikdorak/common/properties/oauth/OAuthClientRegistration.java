package com.jjikmuk.sikdorak.common.properties.oauth;

import java.util.Objects;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.util.UriComponentsBuilder;

@Validated
@Getter
@ConstructorBinding
public class OAuthClientRegistration {

    private static final String AUTHORIZATION_CODE = "authorization_code";

    @NotEmpty
    private final String clientId;
    private final String clientSecret;
    @NotEmpty
    private final String redirectUrl;
    @NotEmpty
    private final String grantType;
    private final String responseType;
    @NotEmpty
    private final String scope;
    @NotEmpty
    private final String authorizationUrl;
    @NotEmpty
    private final String tokenUrl;
    @NotEmpty
    private final String userInfoUrl;
    @NotEmpty
    private final String userNameAttribute;

    public OAuthClientRegistration(String clientId, String clientSecret, String redirectUrl,
        String grantType, String scope, String authorizationUrl, String tokenUrl, String userInfoUrl,
        String userNameAttribute) {
        this.clientId = clientId;
        this.clientSecret = Objects.nonNull(clientSecret) ? clientSecret : "";
        this.redirectUrl = redirectUrl;
        this.grantType = grantType;
        this.responseType = AUTHORIZATION_CODE.equals(grantType) ? "code" : "token";
        this.scope = scope;
        this.authorizationUrl = authorizationUrl;
        this.tokenUrl = tokenUrl;
        this.userInfoUrl = userInfoUrl;
        this.userNameAttribute = userNameAttribute;
    }

    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromHttpUrl(this.authorizationUrl)
            .queryParam("client_id", this.clientId)
            .queryParam("client_secret", this.clientSecret)
            .queryParam("redirect_uri", this.redirectUrl)
            .queryParam("response_type", this.responseType)
            .build().toString();
    }

}
