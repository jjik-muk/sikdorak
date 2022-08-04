package com.jjikmuk.sikdorak.auth.domain;

import com.jjikmuk.sikdorak.auth.controller.response.JwtTokenResponse;
import com.jjikmuk.sikdorak.common.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;

    public JwtTokenResponse createTokenResponse(String payload) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey());

        Date accessTokeExpiredDate = new Date(new Date().getTime() + jwtProperties.getAccessTokenExpiredMillisecond());
        Date refreshTokeExpiredDate = new Date(new Date().getTime() + jwtProperties.getRefreshTokenExpiredMillisecond());

        String accessToken = creatToken(payload, secretKey, accessTokeExpiredDate);
        String refreshToken = creatToken(payload, secretKey, refreshTokeExpiredDate);

        return new JwtTokenResponse(accessToken, refreshToken);
    }


    public boolean validateToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey());

        if (Objects.isNull(token) || token.isEmpty()) {
//            throw new InvalidTokenException();
        }

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return claimsJws.getBody().getExpiration().after(new Date());
        } catch (JwtException e) {
            return false;
        }
    }

    private static String creatToken(String payload, SecretKey secretKey, Date tokenExpiredDate) {
        return Jwts.builder()
                .claim("id", payload)
                .setExpiration(tokenExpiredDate)
                .signWith(secretKey)
                .compact();
    }


}
