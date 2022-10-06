package com.jjikmuk.sikdorak.review.command.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidReviewVisitedDateException;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewVisitedDate {

	@Column
	private LocalDate reviewVisitedDate;

	@Transient
	private LocalDate currentDate;


	public ReviewVisitedDate(LocalDate reviewVisitedDate, LocalDate currentDate) {
		if (Objects.isNull(reviewVisitedDate)
			|| Objects.isNull(currentDate)
			|| !validateReviewVisitedDate(reviewVisitedDate, currentDate)) {
			throw new InvalidReviewVisitedDateException();
		}

		this.reviewVisitedDate = reviewVisitedDate;
	}

	public ReviewVisitedDate(LocalDate reviewVisitedDate) {
		this(reviewVisitedDate, LocalDate.now());
	}


	private boolean validateReviewVisitedDate(LocalDate reviewVisitedDate,
		LocalDate currentDate) {
		return currentDate.isEqual(reviewVisitedDate) || currentDate.isAfter(reviewVisitedDate);
	}
}
