package com.jjikmuk.sikdorak.auth.controller;

import com.jjikmuk.sikdorak.auth.service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/api/oauth/login")
    public ResponseEntity<Void> loginPageUrl() {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location",oAuthService.getLoginPageUrl())
                .build();
    }

    @GetMapping("/api/oauth/callback")
    public ResponseEntity<Void> loginCallback() {

        return ResponseEntity.ok().build();

    }
}
