package com.jjikmuk.sikdorak.review.controller.response;

import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import java.util.List;

public record ReviewListResponse(
    List<ReviewDetailResponse> reviews,
    CursorPageResponse page
) {

    public static ReviewListResponse of(List<ReviewDetailResponse> reviewDetailResponse,
        CursorPageResponse page) {
        return new ReviewListResponse(reviewDetailResponse, page);
    }
}
