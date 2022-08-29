package com.jjikmuk.sikdorak.review.service;

import com.jjikmuk.sikdorak.review.controller.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewModifyRequest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewPagingRequest;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.domain.ReviewVisibility;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.RelationType;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {

	private final StoreRepository storeRepository;
	private final UserRepository userRepository;
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public ReviewDetailResponse searchReviewDetail(LoginUser loginUser, Long reviewId) {
		Review review = reviewRepository.findById(reviewId)
			.orElseThrow(NotFoundReviewException::new);
		User reviewOwner = userRepository.findById(review.getUserId())
			.orElseThrow(NotFoundUserException::new);
		Store store = storeRepository.findById(review.getStoreId())
			.orElseThrow(NotFoundReviewException::new);

		RelationType relationType = reviewOwner.relationTypeTo(loginUser);

		if (!review.isReadable(relationType)) {
			throw new UnauthorizedUserException();
		}

		return ReviewDetailResponse.of(review, store, reviewOwner);
	}

	/*
	TODO - 개선사항
    [] 비회원인 경우
        [] 추천하는 피드들을 우선으로 준다. (좋아요 많은 순? / 평점이 높은 순? / 위치 기반 가까운 순 등등 -> 설정할 수 있도록)
        [] public 상태의 리뷰만 제공한다.
    [] 회원인 경우
        [] 친구들의 피드들을 우선으로 준다. (최신순)
        [] public, protected 상태만 준다.
        [] 유저 본인의 private 게시물도 있다면 함께 보여야 한다.
        [] 인기 피드들만 따로 볼 수 있다. (비회원의 추천 피드와 동일하게)
    	- RecommendedType, RelationType 등의 Enum 활용해서 조건 분기
	 */
	@Transactional(readOnly = true)
	public List<ReviewDetailResponse> getRecommendedReviews(LoginUser loginUser, ReviewPagingRequest pagingRequest) {
		long targetReviewId = pagingRequest.getTargetReviewId();
		PageRequest pageableLimit = PageRequest.ofSize(pagingRequest.getLimitSize());

		List<Review> reviews = reviewRepository.findRecommendedReviews(ReviewVisibility.PUBLIC, targetReviewId, pageableLimit);

		return reviews.stream()
			.map(review -> {
				User reviewOwner = userRepository.findById(review.getUserId())
					.orElseThrow(NotFoundUserException::new);
				Store store = storeRepository.findById(review.getStoreId())
					.orElseThrow(NotFoundReviewException::new);
				return ReviewDetailResponse.of(review, store, reviewOwner);
			})
			.sorted(Comparator.comparing((ReviewDetailResponse::createdAt)).reversed())
			.toList();
	}

	@Transactional
	public Review createReview(LoginUser loginUser, ReviewCreateRequest reviewCreateRequest) {
		User user = userRepository.findById(loginUser.getId())
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
		Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundReviewException::new);
		User user = userRepository.findById(loginUser.getId()).orElseThrow(NotFoundUserException::new);
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
		Review review = reviewRepository.findById(reviewId).orElseThrow(NotFoundReviewException::new);
		User user = userRepository.findById(loginUser.getId()).orElseThrow(NotFoundUserException::new);

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
