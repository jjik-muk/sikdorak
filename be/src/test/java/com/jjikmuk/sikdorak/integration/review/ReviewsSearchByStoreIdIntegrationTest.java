package com.jjikmuk.sikdorak.integration.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.command.domain.ReviewVisibility;
import com.jjikmuk.sikdorak.review.query.ReviewDao;
import com.jjikmuk.sikdorak.review.query.response.ReviewListResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.Authority;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : 특정 가게의 Review 조회 기능")
class ReviewsSearchByStoreIdIntegrationTest extends InitIntegrationTest {

    @Autowired
    private ReviewDao reviewDao;

    @Nested
    @DisplayName("특정 가게의 리뷰를 조회할 때")
    class SearchStoreByStoreName {

        @Test
        @DisplayName("가게가 존재하는 가게라면 리뷰 목록이 반환된다.")
        void reviews_of_store_search_success() {
            // given
            Store store = testData.store;
            CursorPageRequest pageRequest = new CursorPageRequest(0L, 0L, 10, true);
            LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

            // when
            ReviewListResponse reviewResponse = reviewDao.searchReviewsByStoreId(
                store.getId(), loginUser, pageRequest);

            // then
            assertThat(reviewResponse).isNotNull();

            List<ReviewDetailResponse> reviews = reviewResponse.reviews();
            assertThat(reviews).isNotEmpty()
                .allSatisfy(reviewDetail ->
                    assertAll(
                        () -> assertThat(reviewDetail.store().storeId()).isEqualTo(store.getId()),
                        () -> assertThat(reviewDetail.store().storeName()).isEqualTo(store.getStoreName()),
                        () -> assertThat(reviewDetail.store().addressName()).isEqualTo(store.getAddressName()),
                        () -> assertThat(reviewDetail.store().roadAddressName()).isEqualTo(store.getRoadAddressName())
                    )
                );
        }

        @Test
        @DisplayName("가게가 존재하지 않으면 예외를 발생킨다.")
        void reviews_of_store_search_failed() {
            // given
            long notExistingStoreId = Long.MIN_VALUE;
            CursorPageRequest pageRequest = new CursorPageRequest(0L, 0L, 10, true);
            LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

            // then
            assertThatThrownBy(
                () -> reviewDao.searchReviewsByStoreId(notExistingStoreId, loginUser,
                    pageRequest))
                .isInstanceOf(NotFoundStoreException.class);
        }

        @Test
        @DisplayName("리뷰는 PUBLIC 인 항목들만 반환된다.")
        void reviews_of_store_search_only_public_success() {
            // given
            Store store = testData.store;
            CursorPageRequest pageRequest = new CursorPageRequest(0L, 0L, 10, true);
            LoginUser loginUser = new LoginUser(Authority.ANONYMOUS);

            // when
            ReviewListResponse reviewResponse = reviewDao.searchReviewsByStoreId(
                store.getId(), loginUser, pageRequest);

            // then
            assertThat(reviewResponse).isNotNull();

            List<ReviewDetailResponse> reviews = reviewResponse.reviews();
            assertThat(reviews).isNotEmpty()
                .allSatisfy(reviewDetail ->
                    assertThat(reviewDetail.reviewVisibility())
                        .isEqualTo(ReviewVisibility.PUBLIC.name()));
        }
    }
}
