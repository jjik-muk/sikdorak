package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.service.StoreService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("StoreSearch 통합테스트")
public class StoreSearchIntegrationTest extends InitIntegrationTest {

    @Autowired
    private StoreService storeService;

    @Nested
    @DisplayName("가게 이름으로 검색할 때")
    class SearchStoreByStoreName {

        @Test
        @DisplayName("저장된 가게 이름과 일치하면 가게 목록이 반환된다.")
        void exactly_same_store_name_returns_store_list() {
            // given
            String storeName = testData.store.getStoreName();

            // when
            List<StoreSearchResponse> stores = storeService.searchStoresByStoreNameContaining(storeName);

            // then
            assertThat(stores).isNotNull();
            assertThat(stores).isNotEmpty();
            for (StoreSearchResponse store : stores) {
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
            List<StoreSearchResponse> stores = storeService.searchStoresByStoreNameContaining(partOfStoreName);

            // then
            assertThat(stores).isNotNull();
            assertThat(stores).isNotEmpty();
            for (StoreSearchResponse store : stores) {
                assertThat(store.storeName()).contains(partOfStoreName);
            }
        }

        @Test
        @DisplayName("가게 이름이 null 이면 빈 목록이 반환된다.")
        void null_store_name_returns_empty_list() {
            // given
            String storeName = null;

            // when
            List<StoreSearchResponse> stores = storeService.searchStoresByStoreNameContaining(storeName);

            // then
            assertThat(stores).isNotNull();
            assertThat(stores).isEmpty();
        }

        @Test
        @DisplayName("저장된 가게 이름과 일치하는 부분이 없으면 빈 목록이 반환된다.")
        void not_same_store_name_returns_empty_list() {
            // given
            String storeName = "존재하지않는가게이름";

            // when
            List<StoreSearchResponse> stores = storeService.searchStoresByStoreNameContaining(storeName);

            // then
            assertThat(stores).isNotNull();
            assertThat(stores).isEmpty();
        }
    }
}
