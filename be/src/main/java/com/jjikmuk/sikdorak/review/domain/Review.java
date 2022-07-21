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

@Entity
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

	private LocalDate visitedDate;

	public Review(Long id) {
		this.id = id;
		ReviewVisibility.valueOf("asdf");
	}

	//	private List<String> tags;
//	private List<String> images;



}
