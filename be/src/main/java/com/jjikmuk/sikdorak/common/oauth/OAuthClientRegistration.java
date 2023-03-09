package com.jjikmuk.sikdorak.common.oauth;

import com.jjikmuk.sikdorak.common.oauth.converter.OAuthUserAttributesConverter;
import com.jjikmuk.sikdorak.common.properties.oauth.OAuthRegistrationProperty;
import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthUserProfile;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
public class OAuthClientRegistration {

    private static final String AUTHORIZATION_CODE = "authorization_code";

    private final String registrationId;
    private final String clientId;
    private final String clientSecret;
    private final String redirectUrl;
    private final String grantType;
    private final String responseType;
    private final String scope;
    private final String authorizationUrl;
    private final String tokenUrl;
    private final String userInfoUrl;
    private final String userNameAttribute;
    private final OAuthUserAttributesConverter attributesConverter;

    private OAuthClientRegistration(String registrationId, String clientId, String clientSecret,
        String redirectUrl,
        String grantType, String scope, String authorizationUrl, String tokenUrl,
        String userInfoUrl,
        String userNameAttribute, OAuthUserAttributesConverter attributesConverter) {
        this.registrationId = registrationId;
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
        this.attributesConverter = attributesConverter;
    }

    public static OAuthClientRegistration of(String registrationId,
        OAuthRegistrationProperty oAuthRegistrationProperty,
        OAuthUserAttributesConverter attributesConverter) {
        return new OAuthClientRegistration(
            registrationId,
            oAuthRegistrationProperty.getClientId(),
            oAuthRegistrationProperty.getClientSecret(),
            oAuthRegistrationProperty.getRedirectUrl(),
            oAuthRegistrationProperty.getGrantType(),
            oAuthRegistrationProperty.getScope(),
            oAuthRegistrationProperty.getAuthorizationUrl(),
            oAuthRegistrationProperty.getTokenUrl(),
            oAuthRegistrationProperty.getUserInfoUrl(),
            oAuthRegistrationProperty.getUserNameAttribute(),
            attributesConverter);
    }

    public String getAuthorizationUrl() {
        return UriComponentsBuilder.fromHttpUrl(this.authorizationUrl)
            .queryParam("client_id", this.clientId)
            .queryParam("redirect_uri", this.redirectUrl)
            .queryParam("response_type", this.responseType)
            .queryParam("scope", this.scope)
            .build().toString();
    }

    public OAuthUserProfile convert(Map<String, Object> attributes) {
        return attributesConverter.convert(this.userNameAttribute, attributes);
    }
}
