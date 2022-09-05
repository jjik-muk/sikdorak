package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.controller.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("StoreCreate 통합테스트")
public class StoreCreateIntegrationTest extends InitIntegrationTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @Nested
    @DisplayName("가게를 저장할 때")
    class CreateStoreTest {

        @Test
        @DisplayName("정상적인 가게 생성 요청이 주어진다면 가게를 등록할 수 있다")
        void create_store_success() {
            // given
            StoreCreateRequest storeCreateRequest = new StoreCreateRequest(
                "새로 생긴 가게",
                "02-0000-0000",
                "서울시 어쩌구 11-22",
                "서울시 어쩌구 00길 00",
                127.033417,
                37.49082
            );

            // when
            Long savedStoreId = storeService.createStore(storeCreateRequest);

            // then
            Store savedStore = storeRepository.findById(savedStoreId).orElseThrow();
            assertThat(storeCreateRequest.getStoreName()).isEqualTo(savedStore.getStoreName());
            assertThat(storeCreateRequest.getContactNumber()).isEqualTo(savedStore.getContactNumber());
            assertThat(storeCreateRequest.getAddressName()).isEqualTo(savedStore.getAddressName());
            assertThat(storeCreateRequest.getRoadAddressName()).isEqualTo(savedStore.getRoadAddressName());
            assertThat(storeCreateRequest.getX()).isEqualTo(savedStore.getX());
            assertThat(storeCreateRequest.getY()).isEqualTo(savedStore.getY());
        }

        @Test
        @DisplayName("정상적이지 않은 가게 생성 요청이 주어진다면 예외를 발생시킨다")
        void create_store_failed() {
            // given
            StoreCreateRequest invalidStoreCreateRequest = new StoreCreateRequest(
                null,
                "02-0000-0000",
                "서울시 어쩌구 00길 00",
                null,
                37.49082,
                127.033417
            );

            // then
            assertThatThrownBy(() -> storeService.createStore(invalidStoreCreateRequest))
                .isInstanceOf(SikdorakRuntimeException.class);
        }
    }
}
