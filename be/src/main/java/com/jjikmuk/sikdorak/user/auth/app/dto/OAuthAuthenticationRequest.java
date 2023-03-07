package com.jjikmuk.sikdorak.user.auth.app.dto;

import com.jjikmuk.sikdorak.user.auth.app.domain.OAuthClientRegistration;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidAuthorizationCodeException;
import java.util.Objects;
import lombok.Getter;

@Getter
public class OAuthAuthenticationRequest {

    private final OAuthClientRegistration registration;
    private final String authorizationCode;

    private OAuthAuthenticationRequest(OAuthClientRegistration registration,
        String authorizationCode) {
        this.registration = registration;
        this.authorizationCode = validateCode(authorizationCode);
    }

    public static OAuthAuthenticationRequest of(OAuthClientRegistration registration, String code) {
        return new OAuthAuthenticationRequest(registration, code);
    }

    private String validateCode(String code) {
        if (Objects.isNull(code) || code.isEmpty()) {
            throw new InvalidAuthorizationCodeException();
        }
        return code;
    }

}
