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
    private final String redirectUri;
    @NotEmpty
    private final String grantType;
    private final String responseType;
    @NotEmpty
    private final String scope;
    @NotEmpty
    private final String authorizationUri;
    @NotEmpty
    private final String tokenUri;
    @NotEmpty
    private final String userInfoUri;
    @NotEmpty
    private final String userNameAttribute;

    public OAuthClientRegistration(String clientId, String clientSecret, String redirectUri,
        String grantType, String scope, String authorizationUri, String tokenUri, String userInfoUri,
        String userNameAttribute) {
        this.clientId = clientId;
        this.clientSecret = Objects.nonNull(clientSecret) ? clientSecret : "";
        this.redirectUri = redirectUri;
        this.grantType = grantType;
        this.responseType = AUTHORIZATION_CODE.equals(grantType) ? "code" : "token";
        this.scope = scope;
        this.authorizationUri = authorizationUri;
        this.tokenUri = tokenUri;
        this.userInfoUri = userInfoUri;
        this.userNameAttribute = userNameAttribute;
    }

    public String getAuthorizationUri() {
        return UriComponentsBuilder.fromHttpUrl(this.authorizationUri)
            .queryParam("client_id", this.clientId)
            .queryParam("client_secret", this.clientSecret)
            .queryParam("redirect_uri", this.redirectUri)
            .queryParam("response_type", this.responseType)
            .build().toString();
    }

}
