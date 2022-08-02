package com.jjikmuk.sikdorak.review.service;

import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {

	private final StoreService storeService;
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
}
