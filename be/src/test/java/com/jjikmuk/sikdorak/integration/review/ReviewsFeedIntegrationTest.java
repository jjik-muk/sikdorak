package com.jjikmuk.sikdorak.integration.review;

import static org.assertj.core.api.Assertions.assertThat;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewPagingRequest;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("리뷰 피드 목록 조회 통합 테스트")
class ReviewsFeedIntegrationTest extends InitIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    @DisplayName("비회원이 리뷰 피드 목록 조회 요청을 할 경우 public한 리뷰들을 반환한다.")
    void anonymous_user_get_recommended_reviews() {

        LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);
        ReviewPagingRequest pagingRequest = new ReviewPagingRequest(0, 5);

        List<ReviewDetailResponse> recommendedReviews = reviewService.getRecommendedReviews(
            loginUser, pagingRequest);

        assertThat(recommendedReviews).hasSize(5);
    }

    @Test
    @DisplayName("회원이 리뷰 피드 목록 조회 요청을 할 경우 public한 리뷰들을 반환한다.")
    void user_get_recommended_reviews() {

        LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
        ReviewPagingRequest pagingRequest = new ReviewPagingRequest(0, 5);

        List<ReviewDetailResponse> recommendedReviews = reviewService.getRecommendedReviews(
            loginUser, pagingRequest);

        assertThat(recommendedReviews).hasSize(5);
    }

    @Test
    @DisplayName("리뷰 피드 목록 조회 요청시 존재하지 않는 페이지를 요청할 경우 빈배열을 반환한다.")
    void get_invalid_page_recommended_reviews() {

        int invalidPage = Integer.MAX_VALUE;

        LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);
        ReviewPagingRequest pagingRequest = new ReviewPagingRequest(invalidPage, 5);

        List<ReviewDetailResponse> recommendedReviews = reviewService.getRecommendedReviews(
            loginUser, pagingRequest);

        assertThat(recommendedReviews).hasSize(0);
    }
}
