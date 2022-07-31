package com.jjikmuk.sikdorak.auth.domain;

import com.jjikmuk.sikdorak.auth.exception.InvalidUserProfileImageUrlException;
import com.jjikmuk.sikdorak.user.domain.ProfileImage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProfileImageTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 유저의 프로필 이미지 url의 형식이 정상적이지 않은 값이 들어올 경우")
        class Context_with_invalid_image_url {

            @ParameterizedTest
            @NullAndEmptySource
            @MethodSource("provideProfileImageForIsNullAndEmptyAndInvalidForm")
            @DisplayName("예외를 반환한다.")
            void throw_Exception(String profileImageUrl) {
                assertThatThrownBy(() -> new ProfileImage(profileImageUrl))
                        .isInstanceOf(InvalidUserProfileImageUrlException.class);
            }

            private static Stream<Arguments> provideProfileImageForIsNullAndEmptyAndInvalidForm() {
                return Stream.of(
                        Arguments.of("http:/sikdorak"),
                        Arguments.of("http:/sikdorak.com"),
                        Arguments.of(" http://sikdorak.com"),
                        Arguments.of("http://sikdorak.com "),
                        Arguments.of("https:/sikdorak"),
                        Arguments.of("https:/sikdorak.com"),
                        Arguments.of("https//sikdorak$@#.com")
                );
            }

        }
    }
}
