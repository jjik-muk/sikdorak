package com.jjikmuk.sikdorak.review.controller;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/reviews")
    public CommonResponseEntity<Void> insertReview(@RequestBody ReviewInsertRequest reviewInsertRequest) {
        reviewService.insertReview(reviewInsertRequest);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_CREATED, null, HttpStatus.CREATED);
    }
}
