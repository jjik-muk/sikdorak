package com.jjikmuk.sikdorak.review.query.response.reviewdetail.map;

import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailLikeResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailUserResponse;
import com.jjikmuk.sikdorak.store.command.domain.Store;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
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

public record ReviewDetailForMapResponse(
	ReviewDetailUserResponse user,
	ReviewDetailStoreForMapResponse store,
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

	public static ReviewDetailForMapResponse of(Review review, Store store, User author, LoginUser loginUser) {
		boolean likeStatus = !Objects.isNull(loginUser.getId()) && review.isLikedBy(loginUser.getId());
		return new ReviewDetailForMapResponse(
			new ReviewDetailUserResponse(author),
			new ReviewDetailStoreForMapResponse(store),
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
