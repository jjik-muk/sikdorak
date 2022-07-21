package com.jjikmuk.sikdorak.review.controller.request;

import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewInsertRequest {

	@NotNull
	@Size(min = 1, max = 500)
	private String reviewContent;

	@NotNull
	private Long storeId;

	@NotNull
	private Float reviewScore;

	@NotNull
	private String reviewVisibility;

	@NotNull
	@PastOrPresent
	private LocalDate visitedDate;

	private List<String> tags;

	private List<String> images;
}
