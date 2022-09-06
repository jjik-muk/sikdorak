package com.jjikmuk.sikdorak.user.auth.controller;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.properties.JwtProperties;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.controller.response.AccessTokenResponse;
import com.jjikmuk.sikdorak.user.auth.domain.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.service.OAuthService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OAuthController {

    private final OAuthService oAuthService;
    private final JwtProperties jwtProperties;

    @GetMapping("/api/oauth/login")
    public ResponseEntity<Void> loginPageUrl() {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header("Location", oAuthService.getLoginPageUrl())
            .build();
    }

    @GetMapping("/api/oauth/callback")
    public CommonResponseEntity<AccessTokenResponse> loginCallback(@RequestParam String code, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = oAuthService.login(code);
        String refreshToken = jwtTokenPair.getRefreshToken();
        setCookie(response, refreshToken);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.LOGIN_SUCCESS,
            new AccessTokenResponse(jwtTokenPair.getAccessToken()), HttpStatus.OK);
    }

    @GetMapping("/api/oauth/refresh")
    public CommonResponseEntity<AccessTokenResponse> updateAccessToken(@CookieValue("refreshToken") Cookie cookie,  HttpServletResponse response) {
        String refreshToken = cookie.getValue();
        JwtTokenPair jwtTokenPair = oAuthService.updateAccessAndRefreshToken(refreshToken);
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
