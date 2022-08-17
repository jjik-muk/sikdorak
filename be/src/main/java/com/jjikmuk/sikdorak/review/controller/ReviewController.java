package com.jjikmuk.sikdorak.review.controller;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.review.controller.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @UserOnly
    @PostMapping("/api/reviews")
    public CommonResponseEntity<Void> insertReview(
        @AuthenticatedUser LoginUser loginUser,
        @RequestBody ReviewCreateRequest reviewCreateRequest) {
        reviewService.createReview(loginUser, reviewCreateRequest);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.REVIEW_CREATED_SUCCESS, HttpStatus.CREATED);
    }
}
