package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.query.response.UserDetailProfileResponse;
import com.jjikmuk.sikdorak.user.user.query.UserDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * [] 제공해야 하는 정보 : 닉네임, 프로필 이미지, 이메일(있다면), 게시물 개수, 조회자와 유저의 일치 여부, 팔로잉 상태, 팔로워 수, 팔로잉 수
 */

@DisplayName("통합 : User 프로필 조회")
class UserSearchProfileIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserDao userDao;

    @Test
    @DisplayName("유저 본인의 정보를 조회할 경우 유저 정보를 반환한다.")
    void user_search_self_profile() {
        LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);

        UserDetailProfileResponse userDetailProfileResponse = userDao.searchUserDetailProfile(
            testData.kukim.getId(), loginUser);

        assertAll(
            () -> assertThat(userDetailProfileResponse.id()).isEqualTo(testData.kukim.getId()),
            () -> assertThat(userDetailProfileResponse.nickname()).isEqualTo(testData.kukim.getNickname()),
            () -> assertThat(userDetailProfileResponse.relationStatus().isViewer()).isTrue(),
            () -> assertThat(userDetailProfileResponse.relationStatus().followStatus()).isFalse(),
            () -> assertThat(userDetailProfileResponse.reviewCount()).isEqualTo(1),
            () -> assertThat(userDetailProfileResponse.followingCount()).isZero(),
            () -> assertThat(userDetailProfileResponse.followersCount()).isZero()
        );
    }

    @Test
    @DisplayName("비회원이 유저의 정보를 조회할 경우 유저 정보를 반환한다.")
    void anonymous_user_search_user_profile() {
        LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

        UserDetailProfileResponse userDetailProfileResponse = userDao.searchUserDetailProfile(
            testData.kukim.getId(), loginUser);

        assertAll(
            () -> assertThat(userDetailProfileResponse.id()).isEqualTo(testData.kukim.getId()),
            () -> assertThat(userDetailProfileResponse.nickname()).isEqualTo(testData.kukim.getNickname()),
            () -> assertThat(userDetailProfileResponse.relationStatus().isViewer()).isFalse(),
            () -> assertThat(userDetailProfileResponse.relationStatus().followStatus()).isFalse(),
            () -> assertThat(userDetailProfileResponse.reviewCount()).isEqualTo(1),
            () -> assertThat(userDetailProfileResponse.followingCount()).isZero(),
            () -> assertThat(userDetailProfileResponse.followersCount()).isZero()
        );
    }

    @Test
    @DisplayName("팔로우 되어 있는 회원이 유저의 정보를 조회할 경우 유저 정보를 반환한다.")
    void user_search_following_user_profile() {
        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);

        UserDetailProfileResponse userDetailProfileResponse = userDao.searchUserDetailProfile(
            testData.hoi.getId(), loginUser);

        assertAll(
            () -> assertThat(userDetailProfileResponse.id()).isEqualTo(testData.hoi.getId()),
            () -> assertThat(userDetailProfileResponse.nickname()).isEqualTo(testData.hoi.getNickname()),
            () -> assertThat(userDetailProfileResponse.relationStatus().isViewer()).isFalse(),
            () -> assertThat(userDetailProfileResponse.relationStatus().followStatus()).isTrue(),
            () -> assertThat(userDetailProfileResponse.reviewCount()).isEqualTo(3),
            () -> assertThat(userDetailProfileResponse.followingCount()).isEqualTo(2),
            () -> assertThat(userDetailProfileResponse.followersCount()).isEqualTo(1)

        );
    }

    @Test
    @DisplayName("팔로우 되어있지 않은 회원이 유저의 정보를 조회할 경우 유저 정보를 반환한다.")
    void user_search_unfollowing_user_profile() {
        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);

        UserDetailProfileResponse userDetailProfileResponse = userDao.searchUserDetailProfile(
            testData.jay.getId(), loginUser);

        assertAll(
            () -> assertThat(userDetailProfileResponse.id()).isEqualTo(testData.jay.getId()),
            () -> assertThat(userDetailProfileResponse.nickname()).isEqualTo(testData.jay.getNickname()),
            () -> assertThat(userDetailProfileResponse.relationStatus().isViewer()).isFalse(),
            () -> assertThat(userDetailProfileResponse.relationStatus().followStatus()).isFalse(),
            () -> assertThat(userDetailProfileResponse.reviewCount()).isZero(),
            () -> assertThat(userDetailProfileResponse.followingCount()).isZero(),
            () -> assertThat(userDetailProfileResponse.followersCount()).isZero()
        );
    }

    @Test
    @DisplayName("존재하지 않는 유저의 정보를 조회할 경우 예외를 반환한다.")
    void not_found_user_search_another_user_profile() {
        LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);

        assertThatThrownBy(() -> userDao.searchUserDetailProfile(
            9999L, loginUser))
            .isInstanceOf(NotFoundUserException.class);
    }

}
