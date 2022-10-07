package com.jjikmuk.sikdorak.review.query.response.reviewdetail;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record ReviewDetailLikeResponse(

    @NotNull
    @Min(0)
    long count,

    @NotNull
    boolean userLikeStatus

) {

}
