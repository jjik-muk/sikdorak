package com.jjikmuk.sikdorak.review.service;

import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.domain.ReviewContent;
import com.jjikmuk.sikdorak.review.domain.ReviewScore;
import com.jjikmuk.sikdorak.review.domain.ReviewVisibility;
import com.jjikmuk.sikdorak.review.domain.ReviewVisitedDate;
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
	public void insertReview(ReviewInsertRequest request) {
		Long findStoreId = request.getStoreId();
		storeService.findById(findStoreId);

		Review newReview = new Review(request.getStoreId(),
			new ReviewContent(request.getReviewContent()),
			new ReviewScore(request.getReviewScore()),
			ReviewVisibility.create(request.getReviewVisibility()),
			new ReviewVisitedDate(request.getVisitedDate()),
			request.getTags(),
			request.getImages());

		reviewRepository.save(newReview);
	}
}
