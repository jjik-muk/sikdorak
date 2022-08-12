package com.jjikmuk.sikdorak.review.service;

import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.service.StoreService;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {

	private final StoreService storeService;
	private final UserService userService;
	private final ReviewRepository reviewRepository;

	@Transactional
	public Review insertReview(LoginUser loginUser, ReviewInsertRequest reviewInsertRequest) {
		userService.searchById(loginUser.getId());
		storeService.findById(reviewInsertRequest.getStoreId());

		Review newReview = new Review(loginUser.getId(),
			reviewInsertRequest.getStoreId(),
			reviewInsertRequest.getReviewContent(),
			reviewInsertRequest.getReviewScore(),
			reviewInsertRequest.getReviewVisibility(),
			reviewInsertRequest.getVisitedDate(),
			reviewInsertRequest.getTags(),
			reviewInsertRequest.getImages());

		return reviewRepository.save(newReview);
	}
}
