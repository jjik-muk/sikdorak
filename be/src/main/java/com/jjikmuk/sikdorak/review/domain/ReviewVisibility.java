package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidReviewVisibilityException;

public enum ReviewVisibility {
    PUBLIC ,PROTECTED, PRIVATE;

    public static ReviewVisibility create(String visibility) {
        try {
            return valueOf(visibility.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidReviewVisibilityException(e);
        }
    }
}
