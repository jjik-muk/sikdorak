package com.jjikmuk.sikdorak.user.auth.api;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.properties.JwtProperties;
import com.jjikmuk.sikdorak.common.oauth.ClientRegistrationRepository;
import com.jjikmuk.sikdorak.common.oauth.OAuthClientRegistration;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.app.AuthService;
import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthAuthenticationRequest;
import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.app.OAuthService;
import com.jjikmuk.sikdorak.user.auth.app.response.AccessTokenResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthCommandApi {

    private final AuthService authService;
    private final OAuthService oAuthService;
    private final JwtProperties jwtProperties;
    private final ClientRegistrationRepository registrationRepository;

    @GetMapping("/api/oauth/{registrationId}/login")
    public ResponseEntity<Void> loginPageUrl(@PathVariable String registrationId) {
        OAuthClientRegistration registration = registrationRepository.findRegistrationByName(
            registrationId);
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header("Location", registration.getAuthorizationUrl())
            .build();
    }

    @GetMapping("/api/oauth/{registrationId}/callback")
    public CommonResponseEntity<AccessTokenResponse> loginCallback(
        @PathVariable String registrationId, @RequestParam String code,
        HttpServletResponse response) {
        OAuthClientRegistration registration = registrationRepository.findRegistrationByName(
            registrationId);
        JwtTokenPair jwtTokenPair = oAuthService.authenticate(
            OAuthAuthenticationRequest.of(registration, code));
        String refreshToken = jwtTokenPair.getRefreshToken();
        setCookie(response, refreshToken);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.LOGIN_SUCCESS,
            new AccessTokenResponse(jwtTokenPair.getAccessToken()), HttpStatus.OK);
    }

    @GetMapping("/api/oauth/refresh")
    public CommonResponseEntity<AccessTokenResponse> updateAccessToken(
        @CookieValue("refreshToken") Cookie cookie,
        HttpServletResponse response) {
        String refreshToken = cookie.getValue();
        JwtTokenPair jwtTokenPair = authService.reassignAccessToken(refreshToken);
        setCookie(response, jwtTokenPair.getRefreshToken());

        return new CommonResponseEntity<>(ResponseCodeAndMessages.UPDATE_ACCESS_TOKEN_SUCCESS,
            new AccessTokenResponse(jwtTokenPair.getAccessToken()),
            HttpStatus.OK);
    }

    private void setCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookieHeader = ResponseCookie.from("refreshToken", refreshToken)
            .sameSite(SameSite.NONE.attributeValue())
            .maxAge(jwtProperties.getRefreshTokenExpiredMillisecond() / 1000)
            .secure(true)
            .httpOnly(true)
            .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookieHeader.toString());
    }
}
