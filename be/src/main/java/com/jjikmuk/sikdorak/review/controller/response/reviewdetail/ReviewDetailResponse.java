package com.jjikmuk.sikdorak.review.controller.response.reviewdetail;

import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.user.user.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record ReviewDetailResponse(
	ReviewDetailUserResponse user,
	ReviewDetailStoreResponse store,
	long reviewId,
	String reviewContent,
	float reviewScore,
	String reviewVisibility,
	LocalDate visitedDate,
	Set<String> tags,
	List<String> images,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {

	public static ReviewDetailResponse of(Review review, Store store, User user) {
		return new ReviewDetailResponse(
			new ReviewDetailUserResponse(user),
			new ReviewDetailStoreResponse(store),
			review.getId(),
			review.getReviewContent(),
			review.getReviewScore(),
			review.getReviewVisibility(),
			review.getVisitedDate(),
			review.getTags(),
			review.getImages(),
			review.getCreatedAt(),
			review.getUpdatedAt()
		);

	}

}
