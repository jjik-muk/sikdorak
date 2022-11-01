package com.jjikmuk.sikdorak.review.query;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import com.jjikmuk.sikdorak.common.exception.InvalidPageParameterException;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.query.dto.PagingInfo;
import com.jjikmuk.sikdorak.review.query.response.ReviewListForMapResponse;
import com.jjikmuk.sikdorak.review.query.response.ReviewListResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.map.ReviewDetailForMapResponse;
import com.jjikmuk.sikdorak.store.command.domain.Store;
import com.jjikmuk.sikdorak.store.command.domain.UserLocationInfo;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.command.domain.StoreRepository;
import com.jjikmuk.sikdorak.store.query.dto.UserLocationBasedMaxRange;
import com.jjikmuk.sikdorak.store.query.request.UserLocationInfoRequest;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.RelationType;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewDao {

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

        return ReviewDetailResponse.of(review, store, reviewOwner, loginUser);
    }

    @Transactional(readOnly = true)
    public ReviewListResponse getRecentRecommendedReviews(LoginUser loginUser,
        CursorPageRequest cursorPageRequest) {

        validateCursorPageSize(cursorPageRequest);
        PagingInfo pagingInfo = convertToPagingInfo(cursorPageRequest);
        Slice<Review> reviews = getRecommendedReviews(loginUser, pagingInfo);

        List<ReviewDetailResponse> reviewsResponse = getReviewsResponse(reviews.getContent(), loginUser);
        CursorPageResponse cursorPageResponse = getCursorResponse(reviewsResponse,
            reviews.isLast());

        return ReviewListResponse.of(reviewsResponse, cursorPageResponse);

    }

    @Transactional(readOnly = true)
    public ReviewListResponse searchUserReviewsByUserIdAndRelationType(Long searchUserId, LoginUser loginUser,
        CursorPageRequest cursorPageRequest) {

        if (log.isDebugEnabled()) {
            log.debug("searchByUserReviews: searchUserId={}, loginUser.id={}, loginUser.authority={}",
                searchUserId, loginUser.getId(), loginUser.getAuthority());
        }

        validateCursorPageSize(cursorPageRequest);
        PagingInfo pagingInfo = convertToPagingInfo(cursorPageRequest);
        User searchUser = userRepository.findById(searchUserId)
            .orElseThrow(NotFoundUserException::new);

        Slice<Review> userReviews = findUserReviews(loginUser, searchUser, pagingInfo);

        List<ReviewDetailResponse> reviewsResponse = getReviewsResponse(userReviews.getContent(), loginUser);
        CursorPageResponse cursorPageResponse = getCursorResponse(reviewsResponse, userReviews.isLast());

        return ReviewListResponse.of(reviewsResponse, cursorPageResponse);
    }

    @Transactional
    public ReviewListForMapResponse searchUserReviewsByRadius(Long searchUserId, LoginUser loginUser,
        UserLocationInfoRequest userLocationInfoRequest, CursorPageRequest cursorPageRequest) {

        //조회 대상 검증 & 페이징 조건 검증
        validateCursorPageSize(cursorPageRequest);
        PagingInfo pagingInfo = convertToPagingInfo(cursorPageRequest);
        User searchUser = userRepository.findById(searchUserId)
            .orElseThrow(NotFoundUserException::new);

        //클라이언트의 위치 정보 계산
        UserLocationInfo userLocationInfo = new UserLocationInfo(userLocationInfoRequest.x(),
            userLocationInfoRequest.y(), userLocationInfoRequest.radius());
        UserLocationBasedMaxRange coordinates = new UserLocationBasedMaxRange(userLocationInfo);

        //클라이언트와 조회대상의 관계 확인
        //팔로우 관계면 protected 범위의 글까지 제공
        //아니면 public만 제공
        Slice<Review> reviews = getRecommendedReviewsByRadius(loginUser, searchUser,
            pagingInfo, coordinates);

        List<ReviewDetailForMapResponse> reviewsResponse = getMapReviewsResponse(reviews.getContent(), loginUser);
        CursorPageResponse cursorPageResponse = getCursorForMapResponse(reviewsResponse, reviews.isLast());

        return ReviewListForMapResponse.of(reviewsResponse, cursorPageResponse);
    }

    @Transactional(readOnly = true)
    public ReviewListResponse searchReviewsByStoreId(long storeId,
        LoginUser loginUser, CursorPageRequest cursorPageRequest) {

        Store store = storeRepository.findById(storeId)
            .orElseThrow(NotFoundStoreException::new);

        validateCursorPageSize(cursorPageRequest);

        PagingInfo pagingInfo = convertToPagingInfo(cursorPageRequest);

        Slice<Review> reviews = getStoreReviews(store.getId(), pagingInfo);

        List<ReviewDetailResponse> reviewsResponse = getReviewsResponse(reviews.getContent(), loginUser);
        CursorPageResponse cursorPageResponse = getCursorResponse(reviewsResponse,
            reviews.isLast());

        return ReviewListResponse.of(reviewsResponse, cursorPageResponse);
    }

    private Slice<Review> getStoreReviews(long storeId, PagingInfo pagingInfo) {
        return reviewRepository.findPublicReviewsOfStore(storeId, pagingInfo.cursor(),
            pagingInfo.pageable());
    }

    private long getCursorOrDefaultCursor(CursorPageRequest cursorPageRequest) {
        return cursorPageRequest.getAfter() == 0
            ? reviewRepository.findMaxId().orElse(0L)
            : cursorPageRequest.getAfter();
    }

    private List<ReviewDetailResponse> getReviewsResponse(List<Review> reviews, LoginUser loginUser) {

        Map<Long, User> authors = getReviewAuthors(getAuthorIds(reviews));
        Map<Long, Store> stores = getReviewStores(getStoreIds(reviews));

        if (reviews.isEmpty()) {
            return new ArrayList<>();
        }

        return reviews.stream()
            .map(review -> ReviewDetailResponse.of(review,
                stores.get(review.getStoreId()),
                authors.get(review.getUserId()),
                loginUser))
            .toList();
    }

    private List<ReviewDetailForMapResponse> getMapReviewsResponse(List<Review> reviews, LoginUser loginUser) {

        Map<Long, User> authors = getReviewAuthors(getAuthorIds(reviews));
        Map<Long, Store> stores = getReviewStores(getStoreIds(reviews));

        if (reviews.isEmpty()) {
            return new ArrayList<>();
        }

        return reviews.stream()
            .map(review -> ReviewDetailForMapResponse.of(review,
                stores.get(review.getStoreId()),
                authors.get(review.getUserId()),
                loginUser))
            .toList();
    }

    private CursorPageResponse getCursorResponse(List<ReviewDetailResponse> reviewResponses,
        boolean isLast) {

        int lastIndex = reviewResponses.size() - 1;
        long nextCursorId = reviewResponses.isEmpty() ? 0L : reviewResponses.get(lastIndex).reviewId() - 1;

        return new CursorPageResponse(reviewResponses.size(), 0L, nextCursorId, isLast);
    }

    private CursorPageResponse getCursorForMapResponse(List<ReviewDetailForMapResponse> reviewResponses,
        boolean isLast) {

        int lastIndex = reviewResponses.size() - 1;
        long nextCursorId = reviewResponses.isEmpty() ? 0L : reviewResponses.get(lastIndex).reviewId() - 1;

        return new CursorPageResponse(reviewResponses.size(), 0L, nextCursorId, isLast);
    }

    private void validateCursorPageSize(CursorPageRequest cursorPageRequest) {
        if (cursorPageRequest.getSize() > PAGING_LIMIT_SIZE) {
            throw new InvalidPageParameterException();
        }
    }

    private PagingInfo convertToPagingInfo(CursorPageRequest cursorPageRequest) {
        long cursor = getCursorOrDefaultCursor(cursorPageRequest);
        int size = cursorPageRequest.getSize();

        return PagingInfo.from(cursor, size);
    }

    private Slice<Review> getRecommendedReviews(LoginUser loginUser, PagingInfo pagingInfo) {
        if (loginUser.isAnonymous()) {
            return reviewRepository.findPublicRecommendedReviewsInRecentOrder(pagingInfo.cursor(),
                pagingInfo.pageable());
        }

        if (!userRepository.existsById(loginUser.getId())) {
            throw new NotFoundUserException();
        }

        return reviewRepository.findPublicAndProtectedRecommendedReviewsInRecentOrder(
            loginUser.getId(),
            pagingInfo.cursor(),
            pagingInfo.pageable());
    }

    private Slice<Review> getRecommendedReviewsByRadius(LoginUser loginUser, User searchUser,
        PagingInfo pagingInfo,
        UserLocationBasedMaxRange coordinates) {
        if (loginUser.isAnonymous()
            || !RelationType.CONNECTION.equals(searchUser.relationTypeTo(loginUser))) {
            return reviewRepository.findPublicReviewsByRadius(
                searchUser.getId(),
                pagingInfo.cursor(),
                pagingInfo.pageable(),
                coordinates.getMaxX(),
                coordinates.getMaxY(),
                coordinates.getMinX(),
                coordinates.getMinY()
                );
        }

        if (!userRepository.existsById(loginUser.getId())) {
            throw new NotFoundUserException();
        }

        return reviewRepository.findPublicAndProtectedReviewsByRadius(
            searchUser.getId(),
            pagingInfo.cursor(),
            pagingInfo.pageable(),
            coordinates.getMaxX(),
            coordinates.getMaxY(),
            coordinates.getMinX(),
            coordinates.getMinY());
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

    private Slice<Review> findUserReviews(LoginUser loginUser, User searchUser, PagingInfo pagingInfo) {
        return switch (searchUser.relationTypeTo(loginUser)) {
            case SELF -> reviewRepository.findByUserIdWithPageable(searchUser.getId(),
                pagingInfo.cursor(), pagingInfo.pageable());
            case CONNECTION -> reviewRepository.findByUserIdAndConnection(searchUser.getId(),
                pagingInfo.cursor(), pagingInfo.pageable());
            case DISCONNECTION -> reviewRepository.findByUserIdAndDisconnection(searchUser.getId(),
                pagingInfo.cursor(), pagingInfo.pageable());
        };
    }
}
