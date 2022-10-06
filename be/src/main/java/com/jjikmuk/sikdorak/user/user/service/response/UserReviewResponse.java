package com.jjikmuk.sikdorak.user.user.service.response;

import com.jjikmuk.sikdorak.review.command.domain.Review;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UserReviewResponse(

	@NotNull
	long id,

	@NotNull
	long userId,

	@NotNull
	long storeId,

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

