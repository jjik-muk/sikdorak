package com.jjikmuk.sikdorak.review.service.request;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {

	@NotBlank
	@Size(min = 1, max = 500)
	private String reviewContent;

	@NotNull
	@Positive
	private Long storeId;

	@NotNull
	@Pattern(regexp = "1-5")
	private Float reviewScore;

	@NotNull
	@Pattern(regexp = "public\\|protected\\|private")
	private String reviewVisibility;

	@NotNull
	@PastOrPresent
	@Pattern(regexp = "yyyy-MM-dd")
	private LocalDate visitedDate;

	@Size(max = 30)
	private List<String> tags;

	@URL
	@Size(max = 10)
	private List<String> images;

	public ReviewCreateRequest(String reviewContent, Long storeId, Float reviewScore,
		String reviewVisibility, LocalDate visitedDate, List<String> tags,
		List<String> images) {
		this.reviewContent = reviewContent;
		this.storeId = storeId;
		this.reviewScore = reviewScore;
		this.reviewVisibility = reviewVisibility;
		this.visitedDate = visitedDate;
		this.tags = tags;
		this.images = images;
	}
}
