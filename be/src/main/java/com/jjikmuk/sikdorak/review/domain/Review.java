package com.jjikmuk.sikdorak.review.domain;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reviewId")
	private Long id;

	private Long storeId;

	@Embedded
	private ReviewContent reviewContent;

	@Embedded
	private ReviewScore reviewScore;

	@Enumerated(EnumType.STRING)
	private ReviewVisibility reviewVisibility;

	@Embedded
	private ReviewVisitedDate visitedDate;

	@Transient
	private List<String> tags;

	@Transient
	private List<String> images;

	public Review(Long id, Long storeId, String reviewContent, Float reviewScore,
		String reviewVisibility,
		LocalDate visitedDate, List<String> tags, List<String> images) {
		this.id = id;
		this.storeId = storeId;
		this.reviewContent = new ReviewContent(reviewContent);
		this.reviewScore = new ReviewScore(reviewScore);
		this.reviewVisibility = ReviewVisibility.create(reviewVisibility);
		this.visitedDate = new ReviewVisitedDate(visitedDate);
		this.tags = tags;
		this.images = images;
	}


	public Review(Long storeId, String reviewContent, Float reviewScore, String reviewVisibility,
		LocalDate visitedDate, List<String> tags, List<String> images) {
		this(null, storeId, reviewContent, reviewScore, reviewVisibility, visitedDate, tags,
			images);
	}
}
