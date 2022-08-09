package com.jjikmuk.sikdorak.auth.domain;

import com.jjikmuk.sikdorak.auth.controller.response.JwtTokenResponse;
import com.jjikmuk.sikdorak.auth.exception.InvalidTokenException;
import com.jjikmuk.sikdorak.common.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey());
    }

    public JwtTokenResponse createTokenResponse(String payload) {
        String accessToken = createAccessToken(payload);
        String refreshToken = createRefreshToken(payload);

        return new JwtTokenResponse(accessToken, refreshToken);
    }

    public String createAccessToken(String payload) {
        Date accessTokenExpiredDate = new Date(
            new Date().getTime() + jwtProperties.getAccessTokenExpiredMillisecond());

        return buildToken(payload, accessTokenExpiredDate);
    }

    public String createRefreshToken(String payload) {
        Date refreshTokeExpiredDate = new Date(
            new Date().getTime() + jwtProperties.getRefreshTokenExpiredMillisecond());

        return buildToken(payload, refreshTokeExpiredDate);
    }

    // INFO: token값에 대한 null 예외처리는 parseClaimsJws에서 모두 하기 때문에 별도로 작성하지 않았습니다.
    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        }catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

    public String decodeToken(String refreshToken) {
        Claims claim = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(refreshToken)
            .getBody();

        return claim.get("id", String.class);
    }

    private String buildToken(String payload, Date expiredDate) {
        return Jwts.builder()
            .claim("id", payload)
            .setExpiration(expiredDate)
            .signWith(secretKey)
            .compact();
    }
}
