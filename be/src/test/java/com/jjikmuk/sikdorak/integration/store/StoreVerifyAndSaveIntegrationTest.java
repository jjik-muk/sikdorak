package com.jjikmuk.sikdorak.integration.store;

import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_VERIFY_AND_SAVE_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.store.StoreSnippet.STORE_VERIFY_AND_SAVE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.mock.WireMockPlaceApiTest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.controller.request.StoreVerifyAndSaveRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreVerifyAndSaveResponse;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.store.service.StoreService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@WireMockPlaceApiTest
@DisplayName("StoreVerifyAndSave 통합테스트")
class StoreVerifyAndSaveIntegrationTest extends InitIntegrationTest {

	@Autowired
	StoreService storeService;

	@Nested
	@DisplayName("가게 등록을 검증할 때")
	class VerifyAndSaveStoreTest {

		@Test
		@DisplayName("저장되 있는 가게면 가게 정보를 반환한다.")
		void exactly_same_store_name_returns_store_list() {
			// given
			Store savedStore = testData.store;
			Long placeId = savedStore.getPlaceId();
			String storeName = savedStore.getStoreName();

			// when
			StoreVerifyAndSaveResponse response = storeService.verifyAndSave(
				new StoreVerifyAndSaveRequest(placeId, storeName, null, null));

			// then
			assertThat(response).isNotNull();
			assertThat(response.storeId()).isEqualTo(savedStore.getId());
			assertThat(response.storeName()).isEqualTo(savedStore.getStoreName());
		}

		@Test
		@DisplayName("저장되 있지 않지만 장소 API 에서 조회되는 가게면 가게 정보를 반환한다.")
		void partially_same_store_name_returns_store_list() {
			// given
			StoreVerifyAndSaveRequest verifyAndSaveRequest = new StoreVerifyAndSaveRequest(
				1455921244L, "한국계", 127.111, 37.111);

			// when
			StoreVerifyAndSaveResponse response = storeService.verifyAndSave(
				verifyAndSaveRequest);

			// then
			assertThat(response).isNotNull();
			assertThat(response.placeId()).isEqualTo(verifyAndSaveRequest.getPlaceId());
			assertThat(response.storeName()).isEqualTo(verifyAndSaveRequest.getStoreName());
		}

		@Test
		@DisplayName("저장되 있지 않고 장소 API 에서 조회되지 않으면 예외를 발생시킨다.")
		void null_store_name_returns_empty_list() {
			// given
			long notExistingPlaceId = Long.MIN_VALUE;
			StoreVerifyAndSaveRequest verifyAndSaveRequest = new StoreVerifyAndSaveRequest(
				notExistingPlaceId, "없는가게이름입니다", 127.111, 37.111);

			// then
			assertThatThrownBy(() -> storeService.verifyAndSave(verifyAndSaveRequest))
				.isInstanceOf(NotFoundStoreException.class);
		}
	}
}
