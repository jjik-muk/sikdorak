package com.jjikmuk.sikdorak.review.service;

import com.jjikmuk.sikdorak.review.controller.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewModifyRequest;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.RelationType;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRespository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {

	private final StoreRepository storeRepository;
	private final UserRespository userRespository;
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public ReviewDetailResponse searchReviewDetail(LoginUser loginUser, Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(NotFoundReviewException::new);
		User reviewOwner = userRespository.findById(review.getUserId())
			.orElseThrow(NotFoundUserException::new);
		Store store = storeRepository.findById(review.getStoreId())
			.orElseThrow(NotFoundReviewException::new);

		RelationType relationType = reviewOwner.relationTypeTo(loginUser);

		if (!review.isReadable(relationType)) {
			throw new UnauthorizedUserException();
		}

		return ReviewDetailResponse.of(review, store, reviewOwner);
	}

	@Transactional
	public Review createReview(LoginUser loginUser, ReviewCreateRequest reviewCreateRequest) {
		User user = userRespository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);
		Store store = storeRepository.findById(reviewCreateRequest.getStoreId())
			.orElseThrow(NotFoundStoreException::new);

		Review newReview = new Review(user.getId(),
			store.getId(),
			reviewCreateRequest.getReviewContent(),
			reviewCreateRequest.getReviewScore(),
			reviewCreateRequest.getReviewVisibility(),
			reviewCreateRequest.getVisitedDate(),
			reviewCreateRequest.getTags(),
			reviewCreateRequest.getImages());

		return reviewRepository.save(newReview);
	}

	@Transactional
	public Review modifyReview(LoginUser loginUser, Long reviewId,
		ReviewModifyRequest reviewModifyRequest) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(NotFoundReviewException::new);
		User user = userRespository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);
		Store store = storeRepository.findById(reviewModifyRequest.getStoreId()).orElseThrow(
			NotFoundStoreException::new);

		validateReviewWithUser(review, user);

		review.editAll(
			store.getId(),
			reviewModifyRequest.getReviewContent(),
			reviewModifyRequest.getReviewScore(),
			reviewModifyRequest.getReviewVisibility(),
			reviewModifyRequest.getVisitedDate(),
			reviewModifyRequest.getTags(),
			reviewModifyRequest.getImages());

		return review;
	}

	@Transactional
	public Review removeReview(LoginUser loginUser, Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(NotFoundReviewException::new);
		User user = userRespository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);

		validateReviewWithUser(review, user);

		review.delete();

		return review;
	}

	private void validateReviewWithUser(Review review, User user) {
		if (!review.isAuthor(user)) {
			throw new UnauthorizedUserException();
		}
	}
}
