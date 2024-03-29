package com.jjikmuk.sikdorak.integration.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.command.app.ReviewService;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Review 삭제")
class ReviewRemoveIntegrationTest extends InitIntegrationTest {

	@Autowired
	ReviewService reviewService;

	@Test
	@DisplayName("만약 유저가 본인 리뷰에 대한 삭제 요청이 주어진다면 리뷰가 Soft 삭제된다.")
	void remove_review_valid() {
		LoginUser loginUser = LoginUser.user(testData.kukim.getId());
		Long reviewId = testData.kukimPublicReview.getId();

		Review removeReview = reviewService.removeReview(loginUser, reviewId);

		assertThat(removeReview.isDeleted()).isTrue();
	}


	@Test
	@DisplayName("만약 유저가 존재하지 않은 리뷰에 대한 삭제 요청이 주어진다면 예외를 발생시킨다")
	void remove_review_invalid_exist1() {
		LoginUser loginUser = LoginUser.user(testData.kukim.getId());
		long invalidReviewId = Long.MAX_VALUE;

		assertThatThrownBy(() -> reviewService.removeReview(loginUser, invalidReviewId))
			.isInstanceOf(NotFoundReviewException.class);
	}

	@Test
	@DisplayName("만약 유저가 이미 삭제된 리뷰에 대한 삭제 요청이 주어진다면 예외를 발생시킨다")
	void remove_review_invalid_exist2() {
		LoginUser loginUser = LoginUser.user(testData.kukim.getId());
		reviewService.removeReview(loginUser, testData.kukimPublicReview.getId());

		assertThatThrownBy(() -> reviewService.removeReview(loginUser, testData.kukimPublicReview.getId()))
			.isInstanceOf(NotFoundReviewException.class);
	}

	@Test
	@DisplayName("만약 존재하지 않은 유저가 삭제 요청이 주어진다면 예외를 발생시킨다")
	void remove_review_invalid_user() {
		long invalidUserId = Long.MAX_VALUE;
		LoginUser loginUser = LoginUser.user(invalidUserId);
		Long reviewId = testData.kukimPublicReview.getId();

		assertThatThrownBy(() -> reviewService.removeReview(loginUser, reviewId))
			.isInstanceOf(NotFoundUserException.class);
	}

	@Test
	@DisplayName("만약 유저가 다른 유저의 리뷰에 대한 삭제 요청이 주어진다면 예외를 발생시킨다")
	void remove_review_invalid_authorized() {
		LoginUser loginUser = LoginUser.user(testData.jay.getId());
		Long reviewId = testData.kukimPublicReview.getId();

		assertThatThrownBy(() -> reviewService.removeReview(loginUser, reviewId))
			.isInstanceOf(UnauthorizedUserException.class);
	}

}
