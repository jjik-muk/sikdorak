package com.jjikmuk.sikdorak.review.service;

import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.StoreNotFoundException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {

	private final StoreRepository storeRepository;
	private final UserRespository userRespository;
	private final ReviewRepository reviewRepository;

	@Transactional
	public Review insertReview(LoginUser loginUser, ReviewInsertRequest reviewInsertRequest) {
		User user = userRespository.findById(loginUser.getId())
			.orElseThrow(UserNotFoundException::new);
		Store store = storeRepository.findById(reviewInsertRequest.getStoreId())
			.orElseThrow(StoreNotFoundException::new);

		Review newReview = new Review(user.getId(),
			store.getId(),
			reviewInsertRequest.getReviewContent(),
			reviewInsertRequest.getReviewScore(),
			reviewInsertRequest.getReviewVisibility(),
			reviewInsertRequest.getVisitedDate(),
			reviewInsertRequest.getTags(),
			reviewInsertRequest.getImages());

		return reviewRepository.save(newReview);
	}
}
