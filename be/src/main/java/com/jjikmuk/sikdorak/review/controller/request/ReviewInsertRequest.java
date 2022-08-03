package com.jjikmuk.sikdorak.review.controller.request;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewInsertRequest {

	private String reviewContent;

	private Long storeId;

	private Float reviewScore;

	private String reviewVisibility;

	private LocalDate visitedDate;

	private List<String> tags;

	private List<String> images;

	public ReviewInsertRequest(String reviewContent, Long storeId, Float reviewScore,
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
