package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.response.UserProfileResponse;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * [] 제공해야 하는 정보 : 닉네임, 프로필 이미지, 이메일(있다면), 게시물 개수, 조회자와 유저의 일치 여부, 팔로잉 상태, 팔로워 수, 팔로잉 수
 */

@DisplayName("유저 프로필 조회 통합 테스트")
class UserSearchIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("유저 본인의 정보를 조회할 경우 유저 정보를 반환한다.")
    void user_search_self_profile() {
        LoginUser loginUser = new LoginUser(testData.user1.getId(), Authority.USER);

        UserProfileResponse userProfileResponse = userService.searchUserProfile(
            testData.user1.getId(), loginUser);

        assertThat(userProfileResponse.id()).isEqualTo(testData.user1.getId());
        assertThat(userProfileResponse.nickname()).isEqualTo(testData.user1.getNickname());
        assertThat(userProfileResponse.relationStatus().isViewer()).isTrue();
        assertThat(userProfileResponse.relationStatus().followStatus()).isFalse();
        assertThat(userProfileResponse.reviewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("비회원이 유저의 정보를 조회할 경우 유저 정보를 반환한다.")
    void anonymous_user_search_user_profile() {
        LoginUser loginUser = new LoginUser(null, Authority.ANONYMOUS);

        UserProfileResponse userProfileResponse = userService.searchUserProfile(
            testData.user1.getId(), loginUser);

        assertThat(userProfileResponse.id()).isEqualTo(testData.user1.getId());
        assertThat(userProfileResponse.nickname()).isEqualTo(testData.user1.getNickname());
        assertThat(userProfileResponse.relationStatus().isViewer()).isFalse();
        assertThat(userProfileResponse.relationStatus().followStatus()).isFalse();
        assertThat(userProfileResponse.reviewCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("팔로우 되어 있는 회원이 유저의 정보를 조회할 경우 유저 정보를 반환한다.")
    void user_search_following_user_profile() {
        LoginUser loginUser = new LoginUser(testData.followSendUser.getId(), Authority.USER);

        UserProfileResponse userProfileResponse = userService.searchUserProfile(
            testData.followAcceptUser.getId(), loginUser);

        assertThat(userProfileResponse.id()).isEqualTo(testData.followAcceptUser.getId());
        assertThat(userProfileResponse.nickname()).isEqualTo(testData.followAcceptUser.getNickname());
        assertThat(userProfileResponse.relationStatus().isViewer()).isFalse();
        assertThat(userProfileResponse.relationStatus().followStatus()).isTrue();
        assertThat(userProfileResponse.reviewCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("팔로우 되어 있는 회원이 유저의 정보를 조회할 경우 유저 정보를 반환한다.")
    void user_search_unfollowing_user_profile() {
        LoginUser loginUser = new LoginUser(testData.followSendUser.getId(), Authority.USER);

        UserProfileResponse userProfileResponse = userService.searchUserProfile(
            testData.user2.getId(), loginUser);

        assertThat(userProfileResponse.id()).isEqualTo(testData.user2.getId());
        assertThat(userProfileResponse.nickname()).isEqualTo(testData.user2.getNickname());
        assertThat(userProfileResponse.relationStatus().isViewer()).isFalse();
        assertThat(userProfileResponse.relationStatus().followStatus()).isFalse();
        assertThat(userProfileResponse.reviewCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("존재하지 않는 유저의 정보를 조회할 경우 예외를 반환한다.")
    void not_found_user_search_another_user_profile() {
        LoginUser loginUser = new LoginUser(testData.user1.getId(), Authority.USER);

        assertThatThrownBy(() -> userService.searchUserProfile(
            9999L, loginUser))
            .isInstanceOf(NotFoundUserException.class);
    }

}
