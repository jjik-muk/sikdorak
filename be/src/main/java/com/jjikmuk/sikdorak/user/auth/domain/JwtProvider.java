package com.jjikmuk.sikdorak.user.auth.domain;

import com.jjikmuk.sikdorak.common.properties.JwtProperties;
import com.jjikmuk.sikdorak.user.auth.exception.ExpiredTokenException;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey());
    }

    public JwtTokenPair createTokenResponse(String payload) {

        Date accessTokenExpiredDate = new Date(
            new Date().getTime() + jwtProperties.getAccessTokenExpiredMillisecond());
        Date refreshTokeExpiredDate = new Date(
            new Date().getTime() + jwtProperties.getRefreshTokenExpiredMillisecond());


        String accessToken = createAccessToken(payload, accessTokenExpiredDate);
        String refreshToken = createRefreshToken(payload, refreshTokeExpiredDate);

        return new JwtTokenPair(accessToken, refreshToken);
    }

    public String createAccessToken(String payload, Date accessTokenExpiredDate) {
        return buildToken(payload, accessTokenExpiredDate);
    }

    public String createRefreshToken(String payload, Date refreshTokenExpiredDate) {
        return buildToken(payload, refreshTokenExpiredDate);
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(e);
        } catch (IllegalArgumentException | JwtException e) {
            throw new InvalidTokenException(e);
        }
    }

    public String decodeToken(String token) {
        Claims claim = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
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
