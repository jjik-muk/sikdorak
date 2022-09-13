package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.controller.response.RecommendedReviewResponse;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Store 리뷰 조회 기능")
class StoreReviewsSearchByStoreIdIntegrationTest extends InitIntegrationTest {

    @Autowired
    private ReviewService reviewService;

    @Nested
    @DisplayName("특정 가게의 리뷰를 조회할 때")
    class SearchStoreByStoreName {

        @Test
        @DisplayName("가게가 존재하는 가게라면 리뷰 목록이 반환된다.")
        void exactly_same_store_name_returns_store_list() {
            // given
            Store store = testData.store;
            CursorPageRequest pageRequest = new CursorPageRequest(0L, 0L, 10, true);

            // when
            RecommendedReviewResponse reviewResponse = reviewService.searchStoreReviews(
                store.getId(), pageRequest);

            // then
            assertThat(reviewResponse).isNotNull();

            List<ReviewDetailResponse> reviews = reviewResponse.reviews();
            assertThat(reviews).isNotEmpty()
                .allSatisfy(reviewDetail ->
                    assertAll(
                        () -> assertThat(reviewDetail.store().storeId()).isEqualTo(store.getId()),
                        () -> assertThat(reviewDetail.store().storeName()).isEqualTo(
                            store.getStoreName())
                    )
                );
        }

        @Test
        @DisplayName("가게가 존재하지 않으면 예외를 발생킨다.")
        void partially_same_store_name_returns_store_list() {
            // given
            long notExistingStoreId = Long.MIN_VALUE;
            CursorPageRequest pageRequest = new CursorPageRequest(0L, 0L, 10, true);

            // then
            assertThatThrownBy(
                () -> reviewService.searchStoreReviews(notExistingStoreId, pageRequest))
                .isInstanceOf(NotFoundStoreException.class);
        }
    }
}
