package com.jjikmuk.sikdorak.user.domain;

import com.jjikmuk.sikdorak.user.exception.InvalidUserEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class EmailTest {
    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 올바르지 않은 형식의 이메일 값이 들어올 경우")
        class Context_with_invalid_email {

            @ParameterizedTest
            @MethodSource("provideEmailForInvalidForm")
            @DisplayName("예외를 반환한다.")
            void throw_Exception(String email) {
                assertThatThrownBy(() -> new Email(email))
                        .isInstanceOf(InvalidUserEmailException.class);
            }

            private static Stream<Arguments> provideEmailForInvalidForm() {
                return Stream.of(
                        Arguments.of("s"),
                        Arguments.of("name@mailcom"),
                        Arguments.of("name#mail.com"),
                        Arguments.of("name@mail"),
                        Arguments.of("name@mail#.com"),
                        Arguments.of(" name@mail#.com"),
                        Arguments.of("name@mail#.com "),
                        Arguments.of("name@ma-il#.com"),
                        Arguments.of("name@mail.!com")

                );
            }

        }

    }
}
