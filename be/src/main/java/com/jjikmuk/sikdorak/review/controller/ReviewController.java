package com.jjikmuk.sikdorak.review.controller;

import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {


	@PostMapping("/api/reviews")
	@ResponseStatus(HttpStatus.CREATED)
	public void insertReview(@Valid ReviewInsertRequest reviewInsertRequest) {


	}
}
