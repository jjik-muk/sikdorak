package com.jjikmuk.sikdorak.review.controller.request;

import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewPagingRequest implements Serializable{

    private long targetReviewId;
    private int limitSize;

    public ReviewPagingRequest(long targetReviewId, int limitSize) {
        this.targetReviewId = targetReviewId;
        this.limitSize = limitSize;
    }
}

