package com.jjikmuk.sikdorak.unittest.user.auth.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.properties.JwtProperties;
import com.jjikmuk.sikdorak.user.auth.domain.JwtProvider;
import com.jjikmuk.sikdorak.user.auth.exception.ExpiredTokenException;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class JwtProviderTest {

    private JwtProperties jwtProperties;
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        jwtProperties = new JwtProperties("secretKeysecretKeysecretKeysecretKey", 7200000, 604800000);
        jwtProvider = new JwtProvider(jwtProperties);
    }

    @Nested
    @DisplayName("유틸 메서드")
    class Describe_util_method {

        @Nested
        @DisplayName("만약 만료된 토큰이 들어올 경우")
        class Args_with_expired_token {

            @ParameterizedTest
            @ValueSource(strings = {"eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjEiLCJleHAiOjE2NDExNTk5NTh9.yHZkUQs0c-an5wWogvOcZEw6j_rM33gZpnCR9RSSvjk"})
            @DisplayName("예외를 반환한다.")
            void throw_Exception(String token) {
                assertThatThrownBy(() -> jwtProvider.validateToken(token))
                    .isInstanceOf(ExpiredTokenException.class);
            }

        }

        @Nested
        @DisplayName("만약 변조된 토큰이 들어올 경우")
        class Args_with_invalid_token {

            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = {"eyJhbGciOiJIUzI1NiJ9.eyJsdpZCI6IjIiLCJleHAiOjE2NDExNTk5NTh9.p40Wz-_BYcVt2YJJIdjLNxuUvdn92B2VTrQSo2YGwJc"})
            @DisplayName("예외를 반환한다.")
            void throw_Exception(String token) {
                assertThatThrownBy(() -> jwtProvider.validateToken(token))
                    .isInstanceOf(InvalidTokenException.class);
            }

        }

    }

}
