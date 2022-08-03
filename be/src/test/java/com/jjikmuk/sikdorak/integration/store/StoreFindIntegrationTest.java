package com.jjikmuk.sikdorak.integration.store;

import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.store.service.StoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("StoreFind 통합테스트")
public class StoreFindIntegrationTest extends InitIntegrationTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @Nested
    @DisplayName("가게 이름으로 검색할 때")
    class FindStoreByStoreName {

        private static final String STORE_NAME = "맛있는가게";
        private static final String CONTACT_NUMBER = "02-0000-0000";
        private static final String BASE_ADDRESS = "서울시 송파구 좋은길 1";
        private static final String DETAIL_ADDRESS = "1층 101호";
        private static final double LATITUDE = 37.5093890;
        private static final double LONGITUDE = 127.105143;

        Store savedStore = null;

        @BeforeEach
        void setUp() {
            storeRepository.deleteAll();
            savedStore = storeRepository.save(new Store(
                    STORE_NAME,
                    CONTACT_NUMBER,
                    BASE_ADDRESS,
                    DETAIL_ADDRESS,
                    LATITUDE,
                    LONGITUDE
            ));
        }

        @Test
        @DisplayName("저장된 가게 이름과 일치하면 가게 목록이 반환된다.")
        void exactly_same_store_name_returns_store_list() {
            // given
            String storeName = STORE_NAME;

            // when
            List<Store> stores = storeService.findStoresByStoreNameContaining(storeName);

            // then
            assertThat(stores).isNotNull();
            assertThat(stores).isNotEmpty();
            for (Store store : stores) {
                assertThat(store.getStoreName()).contains(storeName);
            }
        }

        @Test
        @DisplayName("저장된 가게 이름과 일부분만 일치해도 가게 목록이 반환된다.")
        void partially_same_store_name_returns_store_list() {
            // given
            String storeName = "가게";

            // when
            List<Store> stores = storeService.findStoresByStoreNameContaining(storeName);

            // then
            assertThat(stores).isNotNull();
            assertThat(stores).isNotEmpty();
            for (Store store : stores) {
                assertThat(store.getStoreName()).contains(storeName);
            }
        }

        @Test
        @DisplayName("가게 이름이 null 이면 빈 목록이 반환된다.")
        void null_store_name_returns_empty_list() {
            // given
            String storeName = null;

            // when
            List<Store> stores = storeService.findStoresByStoreNameContaining(storeName);

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
            List<Store> stores = storeService.findStoresByStoreNameContaining(storeName);

            // then
            assertThat(stores).isNotNull();
            assertThat(stores).isEmpty();
        }
    }
}
