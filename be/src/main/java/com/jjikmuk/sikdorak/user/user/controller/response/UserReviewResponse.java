package com.jjikmuk.sikdorak.user.user.controller.response;

import com.jjikmuk.sikdorak.review.domain.Review;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record UserReviewResponse(
	long id,
	long userId,
	long storeId,
	String reviewContent,
	float reviewScore,
	String reviewVisibility,
	LocalDate visitedDate,
	Set<String> tags,
	List<String> images,
	LocalDateTime createdAt,
	LocalDateTime updatedAt) {

	public static UserReviewResponse from(Review review) {
		return new UserReviewResponse(
			review.getId(),
			review.getUserId(),
			review.getStoreId(),
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

