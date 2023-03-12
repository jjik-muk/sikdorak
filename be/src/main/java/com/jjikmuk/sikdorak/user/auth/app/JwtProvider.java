package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.common.properties.JwtProperties;
import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.exception.ExpiredTokenException;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
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

    public JwtTokenPair createTokenResponse(User user) {

        Date accessTokenExpiredDate = new Date(
            new Date().getTime() + jwtProperties.getAccessTokenExpiredMillisecond());
        Date refreshTokeExpiredDate = new Date(
            new Date().getTime() + jwtProperties.getRefreshTokenExpiredMillisecond());
        Map<String, String> payloads = getPayloads(user);

        String accessToken = createAccessToken(payloads, accessTokenExpiredDate);
        String refreshToken = createRefreshToken(payloads, refreshTokeExpiredDate);

        return new JwtTokenPair(accessToken, refreshToken);
    }

    public String createAccessToken(Map<String, String> payloads, Date accessTokenExpiredDate) {
        return buildToken(payloads, accessTokenExpiredDate);
    }

    public String createRefreshToken(Map<String, String> payloads, Date refreshTokenExpiredDate) {
        return buildToken(payloads, refreshTokenExpiredDate);
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

    public Map<String, String> decodeToken(String token) {
        Claims claim = Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claim.keySet().stream()
            .collect(Collectors.toMap(k -> k, k -> String.valueOf(claim.get(k))));
    }

    public Map<String, String> getPayloads(User user) {
        Map<String, String> payloads = new HashMap<>();
        payloads.put("id", String.valueOf(user.getId()));
        payloads.put("authority", user.getAuthority().name());

        return payloads;
    }

    private String buildToken(Map<String, String> payloads, Date expiredDate) {
        JwtBuilder builder = Jwts.builder();
        payloads.keySet().forEach(key -> builder.claim(key, payloads.get(key)));

        return builder
            .setExpiration(expiredDate)
            .signWith(secretKey)
            .compact();
    }

}
