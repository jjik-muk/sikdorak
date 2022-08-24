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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("UserFollowUnfollow 통합 테스트")
class UserFollowUnfollowIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRespository userRespository;

    @Test
    @DisplayName("팔로우 되어 있지 않은 유저에 대한 팔로우 요청이 들어오면 유저를 팔로우 한다.")
    void user_follow_success() {

        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);
        UserFollowAndUnfollowRequest request = new UserFollowAndUnfollowRequest(
            testData.kukim.getId());

        userService.followUser(loginUser, request);

        Set<Long> sendUserFollowings = userRespository.findFollowings(testData.forky.getId());
        Set<Long> acceptUserFollowers = userRespository.findFollowers(testData.kukim.getId());

        assertThat(sendUserFollowings).contains(testData.kukim.getId());
        assertThat(acceptUserFollowers).contains(testData.forky.getId());
    }

    @Test
    @DisplayName("유저 본인에 대한 팔로우 요청이 들어오면 예외를 반환한다.")
    void user_follow_with_same_user_id() {

        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);
        UserFollowAndUnfollowRequest request = new UserFollowAndUnfollowRequest(
            testData.forky.getId());

        assertThatThrownBy(() -> userService.followUser(loginUser, request))
            .isInstanceOf(DuplicateSendAcceptUserException.class);
    }

    @Test
    @DisplayName("존재하지 않는 유저에 대한 팔로우 요청이 들어오면 예외를 반환한다.")
    void user_follow_with_not_found_user() {

        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);
        UserFollowAndUnfollowRequest request = new UserFollowAndUnfollowRequest(123123123L);

        assertThatThrownBy(() -> userService.followUser(loginUser, request))
            .isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("이미 팔로우 한 유저에 대한 팔로우 요청이 들어오면 예외를 반환한다.")
    void user_follow_with_already_followed_user() {

        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);
        UserFollowAndUnfollowRequest request = new UserFollowAndUnfollowRequest(
            testData.hoi.getId());

        assertThatThrownBy(() -> userService.followUser(loginUser, request))
            .isInstanceOf(DuplicateFollowingException.class);
    }


    @Test
    @DisplayName("팔로우 되어 있는 유저에 대한 언팔로우 요청이 들어오면 언팔로우 한다.")
    void user_unfollow_success() {

        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);
        UserFollowAndUnfollowRequest unfollowRequest = new UserFollowAndUnfollowRequest(
            testData.hoi.getId());

        userService.unfollowUser(loginUser, unfollowRequest);

        Set<Long> sendUserFollowings = userRespository.findFollowings(testData.forky.getId());
        Set<Long> acceptUserFollowers = userRespository.findFollowers(testData.jay.getId());

        assertThat(sendUserFollowings).doesNotContain(testData.jay.getId());
        assertThat(acceptUserFollowers).doesNotContain(testData.forky.getId());
    }

    @Test
    @DisplayName("유저 본인에 대한 언팔로우 요청이 들어오면 예외를 반환한다.")
    void user_unfollow_with_same_user_id() {

        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);
        UserFollowAndUnfollowRequest request = new UserFollowAndUnfollowRequest(
            testData.forky.getId());

        assertThatThrownBy(() -> userService.unfollowUser(loginUser, request))
            .isInstanceOf(DuplicateSendAcceptUserException.class);
    }

    @Test
    @DisplayName("존재하지 않는 유저에 대한 언팔로우 요청이 들어오면 예외를 반환한다.")
    void user_unfollow_with_not_found_user() {

        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);
        UserFollowAndUnfollowRequest request = new UserFollowAndUnfollowRequest(123123123L);

        assertThatThrownBy(() -> userService.unfollowUser(loginUser, request))
            .isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("팔로우 하지 않은 유저에 대한 언팔로우 요청이 들어오면 예외를 반환한다.")
    void user_unfollow_with_unfollowed_user() {

        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);
        UserFollowAndUnfollowRequest unfollowRequest = new UserFollowAndUnfollowRequest(
            testData.kukim.getId());

        assertThatThrownBy(() -> userService.unfollowUser(loginUser, unfollowRequest))
            .isInstanceOf(NotFoundFollowException.class);
    }

}
