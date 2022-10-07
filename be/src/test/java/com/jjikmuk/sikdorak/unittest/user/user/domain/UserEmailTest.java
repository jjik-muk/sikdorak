package com.jjikmuk.sikdorak.unittest.user.user.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.user.user.command.domain.UserEmail;
import com.jjikmuk.sikdorak.user.user.exception.InvalidUserEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : User Email 클래스")
public class UserEmailTest {
    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 올바르지 않은 형식의 이메일 값이 들어올 경우")
        class Context_with_invalid_email {

            @ParameterizedTest
            @ValueSource(strings = {
                    "s",
                    "name@mailcom",
                    "name#mail.com",
                    "name@mail",
                    "name@mail#.com",
                    " name@mail#.com",
                    "name@mail#.com ",
                    "name@ma-il#.com",
                    "name@mail.!com"
            })
            @DisplayName("예외를 반환한다.")
            void throw_Exception(String email) {
                assertThatThrownBy(() -> new UserEmail(email))
                        .isInstanceOf(InvalidUserEmailException.class);
            }
        }
    }
}
