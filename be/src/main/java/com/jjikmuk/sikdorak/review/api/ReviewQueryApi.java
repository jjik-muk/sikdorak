package com.jjikmuk.sikdorak.review.api;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.controller.CursorPageable;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.review.query.ReviewDao;
import com.jjikmuk.sikdorak.review.query.response.ReviewListResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.api.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewQueryApi {

	private final ReviewDao reviewDao;

	@GetMapping("/{reviewId}")
	public CommonResponseEntity<ReviewDetailResponse> searchReviewDetail(
		@AuthenticatedUser LoginUser loginUser,
		@PathVariable Long reviewId) {
		ReviewDetailResponse reviewDetailResponse = reviewDao.searchReviewDetail(loginUser,
			reviewId);

		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.REVIEW_SEARCH_DETAIL_SUCCESS,
			reviewDetailResponse,
			HttpStatus.OK);
	}

	@GetMapping
	public CommonResponseEntity<ReviewListResponse> getRecommendedReviews(
		@AuthenticatedUser LoginUser loginUser,
		// @RequestParam RecommendationType recommendationType, -> 아키텍처 변경되면 사용하지 않을것으로 판단되어 주석처리
		@CursorPageable CursorPageRequest cursorPageRequest) {

		ReviewListResponse recommendedReviews = reviewDao.getRecentRecommendedReviews(
			loginUser, cursorPageRequest);

		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.REVIEWS_FEED_SUCCESS,
			recommendedReviews,
			HttpStatus.OK
		);
	}
}
