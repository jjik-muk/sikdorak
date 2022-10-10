package com.jjikmuk.sikdorak.review.api;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.image.command.app.ImageMetaDataService;
import com.jjikmuk.sikdorak.review.command.app.ReviewService;
import com.jjikmuk.sikdorak.review.command.app.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.command.app.request.ReviewModifyRequest;
import com.jjikmuk.sikdorak.user.auth.api.AuthenticatedUser;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewCommandApi {

	private final ReviewService reviewService;
	private final ImageMetaDataService imageMetaDataService;

	@PostMapping
	@UserOnly
	public CommonResponseEntity<Void> createReview(
		@AuthenticatedUser LoginUser loginUser,
		@RequestBody ReviewCreateRequest reviewCreateRequest) {
		reviewService.createReview(loginUser, reviewCreateRequest);
		imageMetaDataService.updateImageMetaData(reviewCreateRequest.getImages(), loginUser);

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

	@PutMapping("/{reviewId}/like")
	@UserOnly
	public CommonResponseEntity<Void> likeReview(
		@PathVariable Long reviewId,
		@AuthenticatedUser LoginUser loginUser
	) {
		reviewService.likeReview(reviewId, loginUser);

		return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_LIKE_SUCCESS,
			HttpStatus.OK);
	}

	@PutMapping("/{reviewId}/unlike")
	@UserOnly
	public CommonResponseEntity<Void> unlikeReview(
		@PathVariable Long reviewId,
		@AuthenticatedUser LoginUser loginUser
	) {
		reviewService.unlikeReview(reviewId, loginUser);

		return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_UNLIKE_SUCCESS,
			HttpStatus.OK);
	}

}
