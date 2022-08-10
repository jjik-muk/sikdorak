package com.jjikmuk.sikdorak.auth.controller;

import com.jjikmuk.sikdorak.auth.domain.JwtTokenPair;
import com.jjikmuk.sikdorak.auth.controller.response.SikdorakAccessToken;
import com.jjikmuk.sikdorak.auth.service.OAuthService;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/api/oauth/login")
    public ResponseEntity<Void> loginPageUrl() {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
            .header("Location", oAuthService.getLoginPageUrl())
            .build();
    }

    @GetMapping("/api/oauth/callback")
    public CommonResponseEntity<SikdorakAccessToken> loginCallback(@RequestParam String code, HttpServletResponse response) {
        JwtTokenPair jwtTokenPair = oAuthService.login(code);
        String refreshToken = jwtTokenPair.getRefreshToken();
        setCookie(response, refreshToken);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.LOGIN_SUCCESS,
            new SikdorakAccessToken(jwtTokenPair.getAccessToken()), HttpStatus.OK);
    }

    @GetMapping("/api/oauth/refresh")
    public CommonResponseEntity<SikdorakAccessToken> updateAccessToken(@CookieValue("refreshToken") Cookie cookie) {
        String refreshToken = cookie.getValue();

        return new CommonResponseEntity<>(ResponseCodeAndMessages.UPDATE_ACCESS_TOKEN_SUCCESS,
            oAuthService.updateAccessToken(refreshToken),
            HttpStatus.OK);
    }

    private static void setCookie(HttpServletResponse response, String refreshToken) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
