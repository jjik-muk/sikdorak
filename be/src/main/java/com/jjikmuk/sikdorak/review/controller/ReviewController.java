package com.jjikmuk.sikdorak.review.controller;

import com.jjikmuk.sikdorak.common.BaseResponse;
import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.controller.response.ReviewInsertResponse;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.store.service.StoreService;
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

	private final StoreService storeService;
	private final ReviewService reviewService;

	@PostMapping("/api/reviews")
	public ResponseEntity<BaseResponse<ReviewInsertResponse>> insertReview(@Valid @RequestBody ReviewInsertRequest reviewInsertRequest) {
		if (!storeService.existsById(reviewInsertRequest.getStoreId())) {
			return new ResponseEntity<>(new BaseResponse<>("400", "리뷰를 추가할 수 없습니다.", null),
				HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(
			new BaseResponse<>("201", "리뷰 생성 성공했습니다.", reviewService.insertReview(reviewInsertRequest)),
			HttpStatus.CREATED);
	}
}
