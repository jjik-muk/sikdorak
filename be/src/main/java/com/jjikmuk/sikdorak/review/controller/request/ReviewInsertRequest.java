package com.jjikmuk.sikdorak.review.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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
}
