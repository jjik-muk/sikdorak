package com.jjikmuk.sikdorak.review.controller;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.review.controller.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewModifyRequest;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@UserOnly
	@PostMapping("/api/reviews")
	public CommonResponseEntity<Void> createReview(
		@AuthenticatedUser LoginUser loginUser,
		@RequestBody ReviewCreateRequest reviewCreateRequest) {
		reviewService.createReview(loginUser, reviewCreateRequest);

		return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_CREATE_SUCCESS,
			HttpStatus.CREATED);
	}

	@UserOnly
	@PutMapping("/api/reviews/{reviewId}")
	public CommonResponseEntity<Void> modifyReview(
		@PathVariable Long reviewId,
		@AuthenticatedUser LoginUser loginUser,
		@RequestBody ReviewModifyRequest reviewModifyRequest) {
		reviewService.modifyReview(loginUser, reviewId, reviewModifyRequest);

		return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_MODIFY_SUCCESS,
			HttpStatus.OK);
	}

	@UserOnly
	@DeleteMapping("/api/reviews/{reviewId}")
	public CommonResponseEntity<Void> removeReview(
		@PathVariable Long reviewId,
		@AuthenticatedUser LoginUser loginUser) {
		reviewService.removeReview(loginUser, reviewId);

		return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_REMOVE_SUCCESS,
			HttpStatus.OK);
	}
}
