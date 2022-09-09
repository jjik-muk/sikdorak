package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.domain.ReviewVisibility;
import com.jjikmuk.sikdorak.store.controller.response.StoreDetailResponse;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.service.StoreService;
import com.jjikmuk.sikdorak.user.user.domain.User;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Store 상세 검색 기능")
public class StoreSearchDetailIntegrationTest extends InitIntegrationTest {

	@Autowired
	private StoreService storeService;

	@Nested
	@DisplayName("가게 상세를 검색할 때")
	class SearchStoreByStoreName {

		@Test
		@DisplayName("존재하는 가게라면 상세 정보가 반환된다.")
		void exactly_same_store_name_returns_store_list() {
			// given
			int expectedReviewCounts = 10;
			double expectedReviewScoreAvg = 3.0;
			User user = testData.jay;
			Store store = testData.generator.store();
			createReviewNTimes(user, store, 3.0f, expectedReviewCounts);

			// when
			StoreDetailResponse storeDetailResponse = storeService.searchDetail(store.getId());

			// then
			assertThat(storeDetailResponse).isNotNull()
				.satisfies(response ->
					assertAll(
						() -> assertThat(response.storeId()).isEqualTo(store.getId()),
						() -> assertThat(response.storeName()).isEqualTo(store.getStoreName()),
						() -> assertThat(response.addressName()).isEqualTo(store.getAddressName()),
						() -> assertThat(response.roadAddressName()).isEqualTo(
							store.getRoadAddressName()),
						() -> assertThat(response.contactNumber()).isEqualTo(
							store.getContactNumber()),
						() -> assertThat(response.x()).isEqualTo(store.getX()),
						() -> assertThat(response.y()).isEqualTo(store.getY()),
						() -> assertThat(response.reviewCounts()).isEqualTo(expectedReviewCounts),
						() -> assertThat(response.reviewScoreAverage()).isEqualTo(
							expectedReviewScoreAvg)
					)
				);
		}

		@Test
		@DisplayName("존재하는 가게이고 관련 리뷰가 없으면 리뷰 개수와 점수가 0인 상세 정보가 반환된다.")
		void partially_same_store_name_returns_store_list() {
			// given
			int expectedReviewCounts = 0;
			double expectedReviewScoreAvg = 0.0;
			Store store = testData.generator.store();

			// when
			StoreDetailResponse storeDetailResponse = storeService.searchDetail(store.getId());

			// then
			assertThat(storeDetailResponse).isNotNull()
				.satisfies(response ->
					assertAll(
						() -> assertThat(response.storeId()).isEqualTo(store.getId()),
						() -> assertThat(response.storeName()).isEqualTo(store.getStoreName()),
						() -> assertThat(response.addressName()).isEqualTo(store.getAddressName()),
						() -> assertThat(response.roadAddressName()).isEqualTo(
							store.getRoadAddressName()),
						() -> assertThat(response.contactNumber()).isEqualTo(
							store.getContactNumber()),
						() -> assertThat(response.x()).isEqualTo(store.getX()),
						() -> assertThat(response.y()).isEqualTo(store.getY()),
						() -> assertThat(response.reviewCounts()).isEqualTo(expectedReviewCounts),
						() -> assertThat(response.reviewScoreAverage()).isEqualTo(
							expectedReviewScoreAvg)
					)
				);
		}

		@Test
		@DisplayName("존재하지 않는 가게라면 예외를 발생시킨다")
		void null_store_name_returns_empty_list() {
			// given
			long notExistingStoreId = Long.MIN_VALUE;

			// then
			assertThatThrownBy(() -> storeService.searchDetail(notExistingStoreId))
				.isInstanceOf(NotFoundStoreException.class);
		}

		void createReviewNTimes(User user, Store store, float score, int count) {
			IntStream.range(0, count)
				.forEach(i ->
					testData.generator
						.review(user, ReviewVisibility.PUBLIC, store.getId(), score)
				);
		}
	}
}
