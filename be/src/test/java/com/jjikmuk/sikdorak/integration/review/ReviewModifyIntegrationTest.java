package com.jjikmuk.sikdorak.integration.review;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewModifyRequest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.Authority;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Review 수정")
class ReviewModifyIntegrationTest extends InitIntegrationTest {


	@Autowired
	ReviewService reviewService;

	@Test
	@DisplayName("만약 유저가 본인 리뷰에 대한 수정 요청이 주어진다면 리뷰를 수정할 수 있다.")
	void modify_review_valid() {
		LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
		ReviewModifyRequest reviewModifyRequest = new ReviewModifyRequest(
			"Modify Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		Review modifyReview = reviewService.modifyReview(loginUser, testData.user1PublicReview.getId(),
			reviewModifyRequest);

		assertThat(modifyReview.getReviewContent()).isEqualTo(
			reviewModifyRequest.getReviewContent());
		assertThat(modifyReview.getUserId()).isEqualTo(testData.kukim.getId());
	}

	@Test
	@DisplayName("존재하지 않는 리뷰에 대해 수정 요청이 주어진다면 예외를 발생시킨다.")
	void modify_review_invalid_review() {
		long invalidReviewId = Long.MAX_VALUE;
		LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
		ReviewModifyRequest reviewModifyRequest = new ReviewModifyRequest(
			"Modify Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		assertThatThrownBy(() ->
			reviewService.modifyReview(loginUser, invalidReviewId, reviewModifyRequest))
			.isInstanceOf(NotFoundReviewException.class);
	}

	@Test
	@DisplayName("존재하지 않는 스토어에 대한 수정 요청이 주어진다면 예외를 발생시킨다.")
	void modify_review_invalid_store() {
		long invalidStoreId = Long.MAX_VALUE;
		LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
		Long reviewId = testData.user1PublicReview.getId();
		ReviewModifyRequest reviewModifyRequest = new ReviewModifyRequest(
			"Modify Test review contents",
			invalidStoreId,
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		assertThatThrownBy(() ->
			reviewService.modifyReview(loginUser, reviewId, reviewModifyRequest))
			.isInstanceOf(NotFoundStoreException.class);
	}

	@Test
	@DisplayName("존재하지 않는 유저에 대한 수정 요청이 주어진다면 예외를 발생시킨다")
	void modify_review_invalid_user() {
		long invalidUserId = Long.MAX_VALUE;
		LoginUser loginUser = new LoginUser(invalidUserId, Authority.USER);
		Long reviewId = testData.user1PublicReview.getId();
		ReviewModifyRequest reviewModifyRequest = new ReviewModifyRequest(
			"Modify Test review contents",
			testData.store.getId(),
			3.f,
			"public",
			LocalDate.of(2022, 1, 1),
			List.of("tag1", "tag2"),
			List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"));

		assertThatThrownBy(() ->
			reviewService.modifyReview(loginUser, reviewId, reviewModifyRequest))
			.isInstanceOf(NotFoundUserException.class);
	}

}
