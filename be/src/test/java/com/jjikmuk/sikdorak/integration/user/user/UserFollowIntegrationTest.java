package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateFollowingException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateSendAcceptUserException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundFollowException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("유저 팔로우 통합 테스트")
class UserFollowIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRespository userRespository;

    private LoginUser loginUser;

    @BeforeEach
    void setup() {
        loginUser = new LoginUser(testData.user1.getId(), Authority.USER);
    }

    @Test
    @DisplayName("올바른 유저 정보가 들어오면 유저를 팔로우 한다.")
    void user_follow_success() {

        UserFollowAndUnfollowRequest request = new UserFollowAndUnfollowRequest(
            testData.user2.getId());

        userService.followUser(loginUser, request);

        Set<Long> user1Followings = userRespository.findFollowings(testData.user1.getId());
        Set<Long> user2Followers = userRespository.findFollowers(testData.user2.getId());

        assertThat(user1Followings.contains(testData.user2.getId())).isTrue();
        assertThat(user2Followers.contains(testData.user1.getId())).isTrue();
    }

    @Test
    @DisplayName("유저 본인의 정보가 들어오면 예외를 반환한다.")
    void user_follow_with_same_user_id() {

        UserFollowAndUnfollowRequest request = new UserFollowAndUnfollowRequest(
            testData.user1.getId());

        assertThatThrownBy(() -> userService.followUser(loginUser, request))
            .isInstanceOf(DuplicateSendAcceptUserException.class);
    }

    @Test
    @DisplayName("존재하지 않는 유저의 정보가 들어오면 예외를 반환한다.")
    void user_follow_with_not_found_user() {

        UserFollowAndUnfollowRequest request2 = new UserFollowAndUnfollowRequest(987654321L);

        assertThatThrownBy(() -> userService.followUser(loginUser, request2))
            .isInstanceOf(NotFoundUserException.class);

    }

    @Test
    @DisplayName("이미 팔로우 한 유저의 정보가 들어오면 예외를 반환한다.")
    void user_follow_with_already_followed_user() {

        UserFollowAndUnfollowRequest request1 = new UserFollowAndUnfollowRequest(
            testData.user2.getId());
        userService.followUser(loginUser, request1);

        UserFollowAndUnfollowRequest request2 = new UserFollowAndUnfollowRequest(
            testData.user2.getId());

        assertThatThrownBy(() -> userService.followUser(loginUser, request2))
            .isInstanceOf(DuplicateFollowingException.class);

    }


    @Test
    @DisplayName("올바른 유저 정보가 들어오면 유저를 언팔로우 한다.")
    void user_unfollow_success() {

        UserFollowAndUnfollowRequest followRequest = new UserFollowAndUnfollowRequest(
            testData.user2.getId());
        userService.followUser(loginUser, followRequest);

        UserFollowAndUnfollowRequest unfollowRequest = new UserFollowAndUnfollowRequest(
            testData.user2.getId());

        userService.unfollowUser(loginUser, unfollowRequest);

        Set<Long> user1Followings = userRespository.findFollowings(testData.user1.getId());
        Set<Long> user2Followers = userRespository.findFollowers(testData.user2.getId());

        assertThat(user1Followings.contains(testData.user2.getId())).isFalse();
        assertThat(user2Followers.contains(testData.user1.getId())).isFalse();

    }

    @Test
    @DisplayName("팔로우 하지 않은 유저의 정보가 들어오면 예외를 반환한다.")
    void user_unfollow_with_unfollowed_user() {

        UserFollowAndUnfollowRequest unfollowRequest = new UserFollowAndUnfollowRequest(
            testData.user2.getId());

        assertThatThrownBy(() -> userService.unfollowUser(loginUser, unfollowRequest))
            .isInstanceOf(NotFoundFollowException.class);

    }

}
