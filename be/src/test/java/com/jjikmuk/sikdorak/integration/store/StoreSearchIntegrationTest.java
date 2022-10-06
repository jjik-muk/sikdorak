package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.query.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.command.domain.Store;
import com.jjikmuk.sikdorak.store.command.domain.StoreRepository;
import com.jjikmuk.sikdorak.store.query.StoreDao;
import java.util.List;
import java.util.function.Consumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Store 검색 기능")
public class StoreSearchIntegrationTest extends InitIntegrationTest {

	@Autowired
	private StoreDao storeDao;

	@Autowired
	private StoreRepository storeRepository;

	private final Consumer<List<?>> notNullAndNotEmptyList = list -> {
		assertThat(list).isNotNull();
		assertThat(list).isNotEmpty();
	};

	private final Consumer<List<?>> notNullAndEmptyList = list -> {
		assertThat(list).isNotNull();
		assertThat(list).isEmpty();
	};

	@Nested
	@DisplayName("가게 이름으로 검색할 때")
	class SearchStoreByStoreName {

		@Test
		@DisplayName("저장된 가게 이름과 일치하면 가게 목록이 반환된다.")
		void exactly_same_store_name_returns_store_list() {
			// given
			String storeName = testData.store.getStoreName();

			// when
			List<StoreSearchResponse> storeSearchResponses = storeDao.searchStoresByStoreNameContaining(
				storeName);

			// then
			assertThat(storeSearchResponses).satisfies(notNullAndNotEmptyList);
			for (StoreSearchResponse store : storeSearchResponses) {
				assertThat(store.storeName()).contains(storeName);
			}
		}

		@Test
		@DisplayName("저장된 가게 이름과 일부분만 일치해도 가게 목록이 반환된다.")
		void partially_same_store_name_returns_store_list() {
			// given
			String storeFullName = testData.store.getStoreName();
			String partOfStoreName = storeFullName.substring(0, storeFullName.length() / 2);

			// when
			List<StoreSearchResponse> storeSearchResponses = storeDao.searchStoresByStoreNameContaining(
				partOfStoreName);

			// then
			assertThat(storeSearchResponses).satisfies(notNullAndNotEmptyList);
			for (StoreSearchResponse response : storeSearchResponses) {
				assertThat(response.storeName()).contains(partOfStoreName);
			}
		}

		@Test
		@DisplayName("가게 이름이 null 이면 빈 목록이 반환된다.")
		void null_store_name_returns_empty_list() {
			// given
			String storeName = null;

			// when
			List<StoreSearchResponse> storeSearchResponses = storeDao.searchStoresByStoreNameContaining(
				storeName);

			// then
			assertThat(storeSearchResponses).satisfies(notNullAndEmptyList);
		}

		@Test
		@DisplayName("저장된 가게 이름과 일치하는 부분이 없으면 빈 목록이 반환된다.")
		void not_same_store_name_returns_empty_list() {
			// given
			String storeName = "존재하지않는가게이름";

			// when
			List<StoreSearchResponse> storeSearchResponses = storeDao.searchStoresByStoreNameContaining(
				storeName);

			// then
			assertThat(storeSearchResponses).satisfies(notNullAndEmptyList);
		}

		@Test
		@DisplayName("삭제된 가게는 조회 결과에 포함되지 않는다.")
		void deleted_store_not_containing_in_result() {
			// given
			Store deletedStore = testData.store;
			storeRepository.deleteById(deletedStore.getId());

			// when
			List<StoreSearchResponse> storeSearchResponses = storeDao.searchStoresByStoreNameContaining(
				deletedStore.getStoreName());

			// then
			assertThat(storeSearchResponses).satisfies(notNullAndEmptyList);
		}
	}
}
