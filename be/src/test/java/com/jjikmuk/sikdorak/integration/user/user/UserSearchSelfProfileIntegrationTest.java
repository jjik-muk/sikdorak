package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.response.UserSimpleProfileResponse;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : User 자신의 프로필 조회")
class UserSearchSelfProfileIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("유저 본인의 프로필 정보를 요청할 경우 유저 프로필 정보를 반환한다.")
    void search_user_self_profile() {
        LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);

        UserSimpleProfileResponse userSimpleProfileResponse = userService.searchSelfProfile(
            loginUser);

        assertThat(userSimpleProfileResponse.id()).isEqualTo(testData.kukim.getId());
        assertThat(userSimpleProfileResponse.nickname()).isEqualTo(testData.kukim.getNickname());
        assertThat(userSimpleProfileResponse.profileImage()).isEqualTo(testData.kukim.getProfileImage());
    }

    @Test
    @DisplayName("존재하지 않는 유저의 프로필 정보를 요청할 경우 예외를 반환한다.")
    void search_not_found_user_self_profile() {
        LoginUser loginUser = new LoginUser(989898989L, Authority.USER);

        assertThatThrownBy(() -> userService.searchSelfProfile(loginUser))
            .isInstanceOf(NotFoundUserException.class);
    }

}
