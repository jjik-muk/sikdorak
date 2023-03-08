package com.jjikmuk.sikdorak.integration.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.command.app.ReviewService;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.exception.DuplicateLikeUserException;
import com.jjikmuk.sikdorak.review.exception.NotFoundLikeUserException;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.Authority;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Review 좋아요 기능")
class ReviewLikeIntegrationTest extends InitIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Test
    @DisplayName("회원이 리뷰에 대한 좋아요 요청을 했을 경우 리뷰의 좋아요를 누른 유저 목록에 추가되어야한다.")
    void like() {
        long reviewId = testData.kukimPublicReview.getId();

        LoginUser loginUser = LoginUser.user(testData.forky.getId());

        Review review = reviewService.likeReview(reviewId, loginUser);

        assertThat(review.isLikedBy(loginUser.getId())).isTrue();
    }

    @Test
    @DisplayName("회원이 리뷰에 대한 좋아요 취소 요청을 했을 경우 리뷰의 좋아요를 누른 유저 목록에서 제거되어야한다.")
    void unlike() {
        // 좋아요 추가 작업
        long reviewId = testData.kukimPublicReview.getId();
        LoginUser loginUser = LoginUser.user(testData.forky.getId());
        reviewService.likeReview(reviewId, loginUser);

        Review review = reviewService.unlikeReview(reviewId, loginUser);

        assertThat(review.isLikedBy(loginUser.getId())).isFalse();
    }

    @Test
    @DisplayName("유저가 이미 좋아요를 누른 게시물이라면 예외를 반환한다.")
    void like_multiple_times() {
        long reviewId = testData.kukimPublicReview.getId();
        LoginUser loginUser = LoginUser.user(testData.forky.getId());
        reviewService.likeReview(reviewId, loginUser);

        assertThatThrownBy(() -> reviewService.likeReview(reviewId, loginUser))
            .isInstanceOf(DuplicateLikeUserException.class);
    }

    @Test
    @DisplayName("유저가 좋아요를 누르지 않은 게시물이라면 예외를 반환한다.")
    void unlike_already_unliked_review() {
        long reviewId = testData.kukimPublicReview.getId();
        LoginUser loginUser = LoginUser.user(testData.forky.getId());

        assertThatThrownBy(() -> reviewService.unlikeReview(reviewId, loginUser))
            .isInstanceOf(NotFoundLikeUserException.class);
    }

}
