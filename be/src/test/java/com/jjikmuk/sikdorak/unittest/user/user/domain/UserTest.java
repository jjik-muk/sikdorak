package com.jjikmuk.sikdorak.unittest.user.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.InvalidAuthorityException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : User 클래스")
public class UserTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 유저 정보가 올바르게 들어올 경우")
        class Context_with_valid_data {

            @Test
            @DisplayName("유저 객체를 반환한다.")
            void createUser() {

                Long id = 1L;
                Long kakaoId = 232323243242L;
                String nickname = "Forky_Ham";
                String profileImageUrl = "https://k.kakaocdn.net/dn/RKydg/btrIGzx2qYz/IHPrmTbYBOqqbH1/img_640x640.jpg";
                String email = "forky@sikdorak.com";

                User user = new User(id, kakaoId, nickname, profileImageUrl, email);

                assertThat(user.getId()).isEqualTo(id);
                assertThat(user.getUniqueId()).isEqualTo(kakaoId);
                assertThat(user.getNickname()).isEqualTo(nickname);
                assertThat(user.getProfileImage()).isEqualTo(profileImageUrl);
                assertThat(user.getEmail()).isEqualTo(email);
            }
        }

        @Nested
        @DisplayName("유저 권한을 부여할 때")
        class Context_with_authority {

            @ParameterizedTest
            @ValueSource(strings = {"USER", "ADMIN"})
            @DisplayName("정상적인 권한이면 성공한다.")
            void createUser(String authorityName) {
                Long id = 1L;
                Long kakaoId = 232323243242L;
                String nickname = "Forky_Ham";
                String profileImageUrl = "https://k.kakaocdn.net/dn/RKydg/btrIGzx2qYz/IHPrmTbYBOqqbH1/img_640x640.jpg";
                String email = "forky@sikdorak.com";

                Authority authority = Authority.valueOf(authorityName);

                User user = new User(id, kakaoId, nickname, profileImageUrl, email, authority);

                assertThat(user.getAuthority()).isEqualTo(authority);
            }

            @Test
            @DisplayName("ANONYMOUS 권한이면 예외를 발생시킨다.")
            void createUserFailed() {
                Long id = 1L;
                Long kakaoId = 232323243242L;
                String nickname = "Forky_Ham";
                String profileImageUrl = "https://k.kakaocdn.net/dn/RKydg/btrIGzx2qYz/IHPrmTbYBOqqbH1/img_640x640.jpg";
                String email = "forky@sikdorak.com";

                assertThatThrownBy(() ->
                    new User(id, kakaoId, nickname, profileImageUrl, email, Authority.ANONYMOUS))
                    .isInstanceOf(InvalidAuthorityException.class);
            }
        }

        @Nested
        @DisplayName("만약 유저 권한을 부여하지 않으면")
        class Context_without_authority {

            @Test
            @DisplayName("유저 권한을 가진 유저 객체를 반환한다.")
            void createUser() {
                Long id = 1L;
                Long kakaoId = 232323243242L;
                String nickname = "Forky_Ham";
                String profileImageUrl = "https://k.kakaocdn.net/dn/RKydg/btrIGzx2qYz/IHPrmTbYBOqqbH1/img_640x640.jpg";
                String email = "forky@sikdorak.com";

                User user = new User(id, kakaoId, nickname, profileImageUrl, email);

                assertThat(user.getAuthority()).isEqualTo(Authority.USER);
            }
        }
    }

}
