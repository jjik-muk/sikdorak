package com.jjikmuk.sikdorak.integration.user.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.query.ReviewDao;
import com.jjikmuk.sikdorak.review.query.response.ReviewListForMapResponse;
import com.jjikmuk.sikdorak.store.query.request.UserLocationInfoRequest;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합: 위치기반 유저의 리뷰목록 조회")
class UserReviewsSearchByRadiusIntegrationTest extends InitIntegrationTest {

    @Autowired
    private ReviewDao reviewDao;

    @Test
    @DisplayName("비회원의 위치기반 유저 리뷰목록 조회 요청을 한다면 public 리뷰 목록을 제공한다.")
    void anonymous_search_reviews_by_radius() {
        long cursorPage = 0;
        int size = 5;
        CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
        UserLocationInfoRequest userLocationInfoRequest = new UserLocationInfoRequest(127.067, 37.6557, 1000);
        LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

        ReviewListForMapResponse reviewListResponse = reviewDao.searchUserReviewsByRadius(
            testData.hoi.getId(), loginUser,
            userLocationInfoRequest, cursorPageRequest);

        assertThat(reviewListResponse.reviews()).hasSize(1);
    }

    @Test
    @DisplayName("회원이 위치기반 자신의 리뷰목록 조회 요청을 한다면 public 리뷰 목록을 제공한다.")
    void search_self_reviews_by_radius() {
        long cursorPage = 0;
        int size = 5;
        CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
        UserLocationInfoRequest userLocationInfoRequest = new UserLocationInfoRequest(127.067, 37.6557, 1000);
        LoginUser loginUser = new LoginUser(testData.hoi.getId(), Authority.USER);

        ReviewListForMapResponse reviewListResponse = reviewDao.searchUserReviewsByRadius(
            testData.hoi.getId(), loginUser,
            userLocationInfoRequest, cursorPageRequest);

        assertThat(reviewListResponse.reviews()).hasSize(3);
    }

    @Test
    @DisplayName("회원이 위치기반 팔로우하지 않은 유저 리뷰목록 조회 요청을 한다면 public 리뷰 목록을 제공한다.")
    void search_unfollowing_user_reviews_by_radius() {
        long cursorPage = 0;
        int size = 5;
        CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
        UserLocationInfoRequest userLocationInfoRequest = new UserLocationInfoRequest(127.067, 37.6557, 1000);
        LoginUser loginUser = new LoginUser(testData.jay.getId(), Authority.USER);

        ReviewListForMapResponse reviewListResponse = reviewDao.searchUserReviewsByRadius(
            testData.hoi.getId(), loginUser,
            userLocationInfoRequest, cursorPageRequest);

        assertThat(reviewListResponse.reviews()).hasSize(1);
    }

    @Test
    @DisplayName("회원이 위치기반 팔로우 한 유저 리뷰목록 조회 요청을 한다면 public, protected 리뷰 목록을 제공한다.")
    void search_following_user_reviews_by_radius() {
        long cursorPage = 0;
        int size = 5;
        CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
        UserLocationInfoRequest userLocationInfoRequest = new UserLocationInfoRequest(127.067, 37.6557, 1000);
        LoginUser loginUser = new LoginUser(testData.forky.getId(), Authority.USER);

        ReviewListForMapResponse reviewListResponse = reviewDao.searchUserReviewsByRadius(
            testData.hoi.getId(), loginUser,
            userLocationInfoRequest, cursorPageRequest);

        assertThat(reviewListResponse.reviews()).hasSize(2);
    }

    @Test
    @DisplayName("유저의 위치정보 반경내에 데이터가 존재하지 않으면 빈 배열을 반환한다.")
    void search_reviews_by_small_radius_and_get_empty_result() {
        long cursorPage = 0;
        int size = 5;
        CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
        UserLocationInfoRequest userLocationInfoRequest = new UserLocationInfoRequest(127.067, 37.6557, 100);
        LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

        ReviewListForMapResponse reviewListResponse = reviewDao.searchUserReviewsByRadius(
            testData.hoi.getId(), loginUser,
            userLocationInfoRequest, cursorPageRequest);

        assertThat(reviewListResponse.reviews()).isEmpty();
    }


}
