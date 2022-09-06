package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.mock.WireMockPlaceApiTest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.controller.request.StoreVerifyOrSaveRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreVerifyOrSaveResponse;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@WireMockPlaceApiTest
@DisplayName("StoreVerifyOrSave 통합테스트")
class StoreVerifyOrSaveIntegrationTest extends InitIntegrationTest {

	@Autowired
	StoreService storeService;

	@Nested
	@DisplayName("가게 등록을 검증할 때")
	class VerifyOrSaveStoreTest {

		@Test
		@DisplayName("저장되 있는 가게면 가게 정보를 반환한다.")
		void exactly_same_store_name_returns_store_list() {
			// given
			Store savedStore = testData.store;
			Long placeId = savedStore.getPlaceId();
			String storeName = savedStore.getStoreName();

			// when
			StoreVerifyOrSaveResponse response = storeService.verifyOrSave(
				new StoreVerifyOrSaveRequest(placeId, storeName, null, null));

			// then
			assertThat(response).isNotNull();
			assertThat(response.storeId()).isEqualTo(savedStore.getId());
			assertThat(response.storeName()).isEqualTo(savedStore.getStoreName());
		}

		@Test
		@DisplayName("저장되 있지 않지만 장소 API 에서 조회되는 가게면 가게 정보를 반환한다.")
		void partially_same_store_name_returns_store_list() {
			// given
			StoreVerifyOrSaveRequest request = new StoreVerifyOrSaveRequest(
				1455921244L, "한국계", 127.111, 37.111);

			// when
			StoreVerifyOrSaveResponse response = storeService.verifyOrSave(
				request);

			// then
			assertThat(response).isNotNull();
			assertThat(response.placeId()).isEqualTo(request.getPlaceId());
			assertThat(response.storeName()).isEqualTo(request.getStoreName());
		}

		@Test
		@DisplayName("저장되 있지 않고 장소 API 에서 조회되지 않으면 예외를 발생시킨다.")
		void null_store_name_returns_empty_list() {
			// given
			long notExistingPlaceId = Long.MIN_VALUE;
			StoreVerifyOrSaveRequest request = new StoreVerifyOrSaveRequest(
				notExistingPlaceId, "없는가게이름입니다", 127.111, 37.111);

			// then
			assertThatThrownBy(() -> storeService.verifyOrSave(request))
				.isInstanceOf(NotFoundStoreException.class);
		}
	}
}
