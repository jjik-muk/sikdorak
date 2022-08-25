package com.jjikmuk.sikdorak.integration.user.user;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.controller.response.FollowUserProfile;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("유저 팔로잉 목록 조회 통합 테스트")
public class UserFollowingsSearchByUserIdIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원의 특정 유저에 대한 팔로잉 목록 요청이 올바를 경우 팔로잉 목록을 반환한다.")
    void search_followings_by_user() {
        LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);

        List<FollowUserProfile> followings = userService.searchFollowingsByUserId(testData.hoi.getId(), loginUser);

        assertThat(followings.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 유저의 팔로잉 목록이 존재하지 않을 경우 비어있는 목록을 반환한다.")
    void search_followers_is_empty() {
        LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

        List<FollowUserProfile> followings = userService.searchFollowingsByUserId(testData.kukim.getId(), loginUser);

        assertThat(followings).isEmpty();
    }
    @Test
    @DisplayName("존재하지 않는 유저에 대한 팔로잉 목록 요청일 경우 예외를 반환한다.")
    void search_not_found_user_followings() {
        LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

        assertThatThrownBy(() -> userService.searchFollowingsByUserId(98989898L, loginUser))
            .isInstanceOf(NotFoundUserException.class);
    }
}
