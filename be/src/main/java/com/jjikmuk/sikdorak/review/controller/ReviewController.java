package com.jjikmuk.sikdorak.review.controller;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.controller.CursorPageable;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.review.controller.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewModifyRequest;
import com.jjikmuk.sikdorak.review.controller.response.RecommendedReviewResponse;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

	private final ReviewService reviewService;

	@GetMapping("/{reviewId}")
	public CommonResponseEntity<ReviewDetailResponse> searchReviewDetail(
		@AuthenticatedUser LoginUser loginUser,
		@PathVariable Long reviewId) {
		ReviewDetailResponse reviewDetailResponse = reviewService.searchReviewDetail(loginUser,
			reviewId);

		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.REVIEW_SEARCH_DETAIL_SUCCESS,
			reviewDetailResponse,
			HttpStatus.OK);
	}

	@GetMapping
	public CommonResponseEntity<RecommendedReviewResponse> getRecommendedReviews(
		@AuthenticatedUser LoginUser loginUser,
//		@RequestParam RecommendationType recommendationType, -> 아키텍처 변경되면 사용하지 않을것으로 판단되어 주석처리
		@CursorPageable CursorPageRequest cursorPageRequest) {

		RecommendedReviewResponse recommendedReviews = reviewService.getRecentRecommendedReviews(
			loginUser, cursorPageRequest);

		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.REVIEWS_FEED_SUCCESS,
			recommendedReviews,
			HttpStatus.OK
		);
	}

	@PostMapping
	@UserOnly
	public CommonResponseEntity<Void> createReview(
		@AuthenticatedUser LoginUser loginUser,
		@RequestBody ReviewCreateRequest reviewCreateRequest) {
		reviewService.createReview(loginUser, reviewCreateRequest);

		return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_CREATE_SUCCESS,
			HttpStatus.CREATED);
	}

	@PutMapping("/{reviewId}")
	@UserOnly
	public CommonResponseEntity<Void> modifyReview(
		@PathVariable Long reviewId,
		@AuthenticatedUser LoginUser loginUser,
		@RequestBody ReviewModifyRequest reviewModifyRequest) {
		reviewService.modifyReview(loginUser, reviewId, reviewModifyRequest);

		return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_MODIFY_SUCCESS,
			HttpStatus.OK);
	}

	@DeleteMapping("/{reviewId}")
	@UserOnly
	public CommonResponseEntity<Void> removeReview(
		@PathVariable Long reviewId,
		@AuthenticatedUser LoginUser loginUser) {
		reviewService.removeReview(loginUser, reviewId);

		return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_REMOVE_SUCCESS,
			HttpStatus.OK);
	}
}
