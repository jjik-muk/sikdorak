package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidReviewVisibilityException;
import com.jjikmuk.sikdorak.user.user.domain.RelationType;

public enum ReviewVisibility {
    PUBLIC ,PROTECTED, PRIVATE;

    public static ReviewVisibility create(String visibility) {
        try {
            return valueOf(visibility.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new InvalidReviewVisibilityException(e);
        }
    }

	public boolean isRead(RelationType relationType) {
		if (relationType.equals(RelationType.SELF)) {
			return true;
		} else if (relationType.equals(RelationType.CONNECTION)) {
			return this.equals(PUBLIC) || this.equals(PROTECTED);
		} else if (relationType.equals(RelationType.DISCONNECTION)) {
			return this.equals(PUBLIC);
		} else {
			return false;
		}
	}
}
