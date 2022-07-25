package com.jjikmuk.sikdorak.review.domain;

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

	public Review(Long id, Long storeId, ReviewContent reviewContent,
		ReviewScore reviewScore, ReviewVisibility reviewVisibility,
		ReviewVisitedDate visitedDate, List<String> tags, List<String> images) {
		this.id = id;
		this.storeId = storeId;
		this.reviewContent = reviewContent;
		this.reviewScore = reviewScore;
		this.reviewVisibility = reviewVisibility;
		this.visitedDate = visitedDate;
		this.tags = tags;
		this.images = images;
	}

	public Review(Long storeId, ReviewContent reviewContent,
		ReviewScore reviewScore, ReviewVisibility reviewVisibility,
		ReviewVisitedDate visitedDate, List<String> tags, List<String> images) {
		this(null, storeId, reviewContent, reviewScore, reviewVisibility, visitedDate, tags,
			images);
	}
}
