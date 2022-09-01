package com.jjikmuk.sikdorak.review.service;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import com.jjikmuk.sikdorak.common.exception.InvalidPageParameterException;
import com.jjikmuk.sikdorak.review.controller.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.controller.request.ReviewModifyRequest;
import com.jjikmuk.sikdorak.review.controller.response.RecommendedReviewResponse;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.exception.DuplicateLikeUserException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final long FIRST_CURSOR_ID = 0L;
    private static final long LAST_CURSOR_ID = 1L;
    private static final int PAGING_LIMIT_SIZE = 15;

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
        [] public 상태의 리뷰만 제공한다.
        [] 추천하는 피드들을 우선으로 준다. (좋아요 많은 순 / 평점이 높은 순 / 댓글 많은 순 등등)
     [] 회원인 경우
        [] public, protected 상태만 준다.
        [] 친구들의 피드들을 우선으로 준다. (최신순)
        [] 유저 본인의 private 게시물도 있다면 함께 보여야 한다.
        [] 추천 조건을 변경해서 피드들을 따로 볼 수 있다. (비회원의 추천 피드와 동일하게)
     */
    @Transactional(readOnly = true)
    public RecommendedReviewResponse getRecentRecommendedReviews(LoginUser loginUser,
        CursorPageRequest cursorPageRequest) {

        if (cursorPageRequest.getSize() > PAGING_LIMIT_SIZE) {
            throw new InvalidPageParameterException();
        }

        long cursor = getCursorOrDefaultCursor(cursorPageRequest);
        int size = cursorPageRequest.getSize();
        Pageable pageable = Pageable.ofSize(size);

        List<Review> reviews = getRecommendedReviews(loginUser, cursor, pageable);

        if (reviews.isEmpty()) {
            return RecommendedReviewResponse.of(
                new ArrayList<>(),
                new CursorPageResponse(0, FIRST_CURSOR_ID, LAST_CURSOR_ID));
        }

        List<ReviewDetailResponse> recommendedReviewsResponse = getRecommendedReviewsResponse(
            reviews,
            getReviewAuthors(getAuthorIds(reviews)),
            getReviewStores(getStoreIds(reviews)));
        long nextCursorId = recommendedReviewsResponse.get(recommendedReviewsResponse.size() - 1)
            .reviewId();
        CursorPageResponse cursorPageResponse = new CursorPageResponse(size, FIRST_CURSOR_ID, nextCursorId);

        return RecommendedReviewResponse.of(recommendedReviewsResponse, cursorPageResponse);

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
    public void likeReview(Long reviewId, LoginUser loginUser) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(NotFoundReviewException::new);
        User user = userRepository.findById(loginUser.getId())
            .orElseThrow(NotFoundUserException::new);

        if (review.isLikedBy(user.getId())) {
            throw new DuplicateLikeUserException();
        }

        review.like(user);
    }



    private void validateReviewWithUser(Review review, User user) {
        if (!review.isAuthor(user)) {
            throw new UnauthorizedUserException();
        }
    }

    private long getCursorOrDefaultCursor(CursorPageRequest cursorPageRequest) {
        return cursorPageRequest.getAfter() == 0 ? reviewRepository.count()
            : cursorPageRequest.getAfter();
    }

    private List<ReviewDetailResponse> getRecommendedReviewsResponse(List<Review> reviews,
        Map<Long, User> authors, Map<Long, Store> stores) {

        return reviews.stream()
            .map(review -> ReviewDetailResponse.of(review, stores.get(review.getStoreId()),
                authors.get(review.getUserId())))
            .toList();
    }

    private List<Review> getRecommendedReviews(LoginUser loginUser, long targetReviewId,
        Pageable pageable) {
        if (loginUser.isAnonymous()) {
            return reviewRepository.findPublicRecommendedReviewsInRecentOrder(targetReviewId,
                pageable);
        }
        return reviewRepository.findPublicAndProtectedRecommendedReviewsInRecentOrder(
            targetReviewId, pageable);
    }

    private Map<Long, Store> getReviewStores(List<Long> storeIds) {
        return storeRepository.findAllById(storeIds).stream()
            .collect(Collectors.toMap(Store::getId, Function.identity()));
    }

    private Map<Long, User> getReviewAuthors(List<Long> authorIds) {
        return userRepository.findAllById(authorIds).stream()
            .collect(Collectors.toMap(User::getId, Function.identity()));
    }

    private List<Long> getStoreIds(List<Review> reviews) {
        return reviews.stream()
            .map(Review::getStoreId)
            .toList();
    }

    private List<Long> getAuthorIds(List<Review> reviews) {
        return reviews.stream()
            .map(Review::getUserId)
            .toList();
    }

}
