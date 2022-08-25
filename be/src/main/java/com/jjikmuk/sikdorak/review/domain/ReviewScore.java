package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidReviewScoreException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.Set;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewScore {

    private static final Set<Float> VALID_VALUES = Set.of(1.0f, 2.0f, 3.0f, 4.0f, 5.0f);

    private Float reviewScore;

    public ReviewScore(Float reviewScore) {
        if (Objects.isNull(reviewScore) ||
                !validateReviewScore(reviewScore)) {
            throw new InvalidReviewScoreException();
        }

        this.reviewScore = reviewScore;
    }

    private boolean validateReviewScore(Float reviewScore) {
        return VALID_VALUES.contains(reviewScore);
    }
}
