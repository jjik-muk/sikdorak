package com.jjikmuk.sikdorak.review.controller;

import com.jjikmuk.sikdorak.common.BaseResponse;
import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

	private final ReviewService reviewService;

	@PostMapping("/api/reviews")
	public ResponseEntity<BaseResponse<Void>> insertReview(@RequestBody ReviewInsertRequest reviewInsertRequest) {
		reviewService.insertReview(reviewInsertRequest);

		return new ResponseEntity<>(
			new BaseResponse<>("201", "리뷰 생성 성공했습니다.", null),
			HttpStatus.CREATED);
	}
}
