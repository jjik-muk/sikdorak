package com.jjikmuk.sikdorak.integration.review;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("리뷰 디테일 조회 통합 테스트")
class ReviewSearchIntegrationTest extends InitIntegrationTest {

	@Autowired
	ReviewService reviewService;

	@Test
	@DisplayName("유저가 본인의 전체 공개 리뷰를 조회한 경우 디테일 리뷰응답 객체를 반환한다.")
	void search_public_review_detail_with_owner_user() {
		LoginUser reviewOwnerUser = new LoginUser(testData.hoi.getId(),
			Authority.USER);
		Long searchReviewId = testData.followAcceptUserPublicReview.getId();

		ReviewDetailResponse reviewDetailResponse = reviewService.searchReviewDetail(
			reviewOwnerUser, searchReviewId);

		assertThat(reviewDetailResponse.reviewId()).isEqualTo(searchReviewId);
		assertThat(reviewDetailResponse.user().userId()).isEqualTo(reviewOwnerUser.getId());
		assertThat(reviewDetailResponse.reviewVisibility()).isEqualTo("PUBLIC");
	}

	@Test
	@DisplayName("유저가 본인의 비공개 리뷰를 조회한 경우 디테일 리뷰응답 객체를 반환한다.")
	void search_private_review_detail_with_owner_user() {
		LoginUser reviewOwnerUser = new LoginUser(testData.hoi.getId(),
			Authority.USER);
		Long searchReviewId = testData.followAcceptUserPrivateReview.getId();

		ReviewDetailResponse reviewDetailResponse = reviewService.searchReviewDetail(
			reviewOwnerUser, searchReviewId);

		assertThat(reviewDetailResponse.reviewId()).isEqualTo(searchReviewId);
		assertThat(reviewDetailResponse.user().userId()).isEqualTo(reviewOwnerUser.getId());
		assertThat(reviewDetailResponse.reviewVisibility()).isEqualTo("PRIVATE");
	}

	@Test
	@DisplayName("유저가 친구의 친구 공개 리뷰를 조회한 경우 리뷰응답 객체를 반환한다")
	void search_protected_review_detail_with_friend_User() {
		LoginUser reviewFriendUser = new LoginUser(testData.forky.getId(), Authority.USER);
		Long searchReviewId = testData.followAcceptUserProtectedReview.getId();

		ReviewDetailResponse reviewDetailResponse = reviewService.searchReviewDetail(
			reviewFriendUser, searchReviewId);

		assertThat(reviewDetailResponse.reviewId()).isEqualTo(searchReviewId);
		assertThat(reviewDetailResponse.user().userId()).isEqualTo(testData.hoi.getId());
		assertThat(reviewDetailResponse.reviewVisibility()).isEqualTo("PROTECTED");
	}

	@Test
	@DisplayName("존재하지않는 리뷰를 조회한 경우 예외를 발생시킨다")
	void search_not_exist_review_with_guest() {
		LoginUser guestUser = new LoginUser(Authority.ANONYMOUS);
		Long searchReviewId = Long.MAX_VALUE;

		assertThatThrownBy(() ->  reviewService.searchReviewDetail(
			guestUser, searchReviewId))
			.isInstanceOf(NotFoundReviewException.class);
	}

	@Test
	@DisplayName("유저가 읽을 권한이 없는 리뷰를 조회한 경우 예외를 발생시킨다")
	void search_private_review_detail_with_guest() {
		LoginUser guestUser = new LoginUser(Authority.ANONYMOUS);
		Long searchReviewId = testData.followAcceptUserPrivateReview.getId();

		assertThatThrownBy(() ->  reviewService.searchReviewDetail(
			guestUser, searchReviewId))
			.isInstanceOf(UnauthorizedUserException.class);
	}

}
