package com.jjikmuk.sikdorak.review.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.jjikmuk.sikdorak.review.exception.InvalidReviewContentException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewContent {

	private static final int LIMIT_LENGTH = 500;

	@Column(length = LIMIT_LENGTH)
	private String reviewContent;

	public ReviewContent(String reviewContent) {
		if (Objects.isNull(reviewContent) ||
			reviewContent.isBlank() ||
			reviewContent.length() > LIMIT_LENGTH) {
			throw new InvalidReviewContentException();
		}

		this.reviewContent = reviewContent;
	}
}
