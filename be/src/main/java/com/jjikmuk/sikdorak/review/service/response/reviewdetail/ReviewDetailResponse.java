package com.jjikmuk.sikdorak.review.service.response.reviewdetail;

import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record ReviewDetailResponse(
	ReviewDetailUserResponse user,
	ReviewDetailStoreResponse store,
	ReviewDetailLikeResponse like,

	@NotNull
	long reviewId,

	@NotBlank
	@Size(min = 1, max = 500)
	String reviewContent,

	@NotNull
	@Pattern(regexp = "1-5")
	float reviewScore,

	@NotNull
	@Pattern(regexp = "public\\|protected\\|private")
	String reviewVisibility,

	@NotNull
	@PastOrPresent
	@Pattern(regexp = "yyyy-MM-dd")
	LocalDate visitedDate,

	@Size(max = 30)
	Set<String> tags,

	@URL
	@Size(max = 10)
	List<String> images,

	@NotNull
	@Pattern(regexp = "yyyy-MM-dd")
	LocalDateTime createdAt,

	@NotNull
	@Pattern(regexp = "yyyy-MM-dd")
	LocalDateTime updatedAt
) {

	public static ReviewDetailResponse of(Review review, Store store, User author, LoginUser loginUser) {
		boolean likeStatus = !Objects.isNull(loginUser.getId()) && review.isLikedBy(loginUser.getId());
		return new ReviewDetailResponse(
			new ReviewDetailUserResponse(author),
			new ReviewDetailStoreResponse(store),
			new ReviewDetailLikeResponse(review.getLikesCount(), likeStatus),
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
