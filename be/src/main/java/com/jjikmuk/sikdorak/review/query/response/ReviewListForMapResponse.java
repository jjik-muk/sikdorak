package com.jjikmuk.sikdorak.review.query.response;

import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.map.ReviewDetailForMapResponse;
import java.util.List;

public record ReviewListForMapResponse(
    List<ReviewDetailForMapResponse> reviews,
    CursorPageResponse page
) {

    public static ReviewListForMapResponse of(List<ReviewDetailForMapResponse> reviewDetailResponse,
        CursorPageResponse page) {
        return new ReviewListForMapResponse(reviewDetailResponse, page);
    }
}
