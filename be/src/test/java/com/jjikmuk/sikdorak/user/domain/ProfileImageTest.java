package com.jjikmuk.sikdorak.user.domain;

import com.jjikmuk.sikdorak.auth.exception.InvalidUserProfileImageUrlException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("User ProfileImage 단위 테스트")
public class ProfileImageTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 유저의 프로필 이미지 url의 형식이 정상적이지 않은 값이 들어올 경우")
        class Context_with_invalid_image_url {

            @ParameterizedTest
            @NullAndEmptySource
            @ValueSource(strings = {
                    "http:/sikdorak",
                    "http:/sikdorak.com",
                    " http://sikdorak.com",
                    "http://sikdorak.com ",
                    "https:/sikdorak",
                    "https:/sikdorak.com",
                    "https//sikdorak$@#.com"
            })
            @DisplayName("예외를 반환한다.")
            void throw_Exception(String profileImageUrl) {
                assertThatThrownBy(() -> new ProfileImage(profileImageUrl))
                        .isInstanceOf(InvalidUserProfileImageUrlException.class);
            }
        }
    }
}
