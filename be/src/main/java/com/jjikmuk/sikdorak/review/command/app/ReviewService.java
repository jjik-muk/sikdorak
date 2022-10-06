package com.jjikmuk.sikdorak.review.command.app;

import com.jjikmuk.sikdorak.review.command.app.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.command.app.request.ReviewModifyRequest;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.review.exception.DuplicateLikeUserException;
import com.jjikmuk.sikdorak.review.exception.NotFoundLikeUserException;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import com.jjikmuk.sikdorak.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

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
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(NotFoundReviewException::new);
        User user = userRepository.findById(loginUser.getId())
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
        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);

        validateReviewWithUser(review, user);

        review.delete();

        return review;
    }

    @Transactional
    public Review likeReview(Long reviewId, LoginUser loginUser) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(NotFoundReviewException::new);
        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);

        if (review.isLikedBy(user.getId())) {
            throw new DuplicateLikeUserException();
        }

        review.like(user);
        return review;
    }

    @Transactional
    public Review unlikeReview(long reviewId, LoginUser loginUser) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(NotFoundReviewException::new);
        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);

        if (!review.isLikedBy(user.getId())) {
            throw new NotFoundLikeUserException();
        }

        review.unlike(user);
        return review;
    }

    private void validateReviewWithUser(Review review, User user) {
        if (!review.isAuthor(user)) {
            throw new UnauthorizedUserException();
        }
    }
}
