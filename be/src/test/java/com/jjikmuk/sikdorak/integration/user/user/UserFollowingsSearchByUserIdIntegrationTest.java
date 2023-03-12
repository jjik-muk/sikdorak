package com.jjikmuk.sikdorak.integration.user.user;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.query.UserDao;
import com.jjikmuk.sikdorak.user.user.query.response.FollowUserProfile;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : User 팔로잉 목록 조회")
class UserFollowingsSearchByUserIdIntegrationTest extends InitIntegrationTest {

    @Autowired
    private UserDao userDao;

    @Test
    @DisplayName("회원의 특정 유저에 대한 팔로잉 목록 요청이 올바를 경우 팔로잉 목록을 반환한다.")
    void search_followings_by_user() {
        LoginUser loginUser = LoginUser.user(testData.kukim.getId());

        List<FollowUserProfile> followings = userDao.searchFollowingsByUserId(testData.hoi.getId(), loginUser);

        assertThat(followings).hasSize(2);
    }

    @Test
    @DisplayName("특정 유저의 팔로잉 목록이 존재하지 않을 경우 비어있는 목록을 반환한다.")
    void search_followers_is_empty() {
        LoginUser loginUser = LoginUser.anonymous();

        List<FollowUserProfile> followings = userDao.searchFollowingsByUserId(testData.kukim.getId(), loginUser);

        assertThat(followings).isEmpty();
    }
    @Test
    @DisplayName("존재하지 않는 유저에 대한 팔로잉 목록 요청일 경우 예외를 반환한다.")
    void search_not_found_user_followings() {
        LoginUser loginUser = LoginUser.anonymous();

        assertThatThrownBy(() -> userDao.searchFollowingsByUserId(98989898L, loginUser))
            .isInstanceOf(NotFoundUserException.class);
    }
}
