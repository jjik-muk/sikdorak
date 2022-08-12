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
	public Long insertReview(ReviewInsertRequest request) {
		Long findStoreId = request.getStoreId();
		storeService.findById(findStoreId);

		Review newReview = new Review(request.getStoreId(),
			request.getReviewContent(),
			request.getReviewScore(),
			request.getReviewVisibility(),
			request.getVisitedDate(),
			request.getTags(),
			request.getImages());

		Review saveReview = reviewRepository.save(newReview);
		return saveReview.getId();
	}

	public Long insertReview(LoginUser loginUser, ReviewInsertRequest reviewInsertRequest) {
		userService.searchById(loginUser.getId());
		storeService.findById(reviewInsertRequest.getStoreId());

		throw new UnsupportedOperationException("ReviewService#insertReview 아직 구현하지 않음 :)");

	}
}
