package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.controller.request.StoreInsertRequest;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("StoreInsert 통합테스트")
public class StoreInsertIntegrationTest extends InitIntegrationTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @Nested
    @DisplayName("가게를 저장할 때")
    class InsertStoreTest {

        @Test
        @DisplayName("정상적인 가게 생성 요청이 주어진다면 가게를 등록할 수 있다")
        void create_store_success() {
            // given
            StoreInsertRequest storeInsertRequest = new StoreInsertRequest(
                "새로 생긴 가게",
                "02-0000-0000",
                "서울시 어쩌구 00길 00",
                null,
                37.49082,
                127.033417
            );

            // when
            Long savedStoreId = storeService.insertStore(storeInsertRequest);

            // then
            Store savedStore = storeRepository.findById(savedStoreId).orElseThrow();
            assertThat(storeInsertRequest.getStoreName()).isEqualTo(savedStore.getStoreName());
            assertThat(storeInsertRequest.getContactNumber()).isEqualTo(savedStore.getContactNumber());
            assertThat(storeInsertRequest.getBaseAddress()).isEqualTo(savedStore.getBaseAddress());
            assertThat(storeInsertRequest.getDetailAddress()).isEqualTo(savedStore.getDetailAddress());
            assertThat(storeInsertRequest.getLatitude()).isEqualTo(savedStore.getLatitude());
            assertThat(storeInsertRequest.getLongitude()).isEqualTo(savedStore.getLongitude());
        }

        @Test
        @DisplayName("정상적이지 않은 가게 생성 요청이 주어진다면 예외를 발생시킨다")
        void create_store_failed() {
            // given
            StoreInsertRequest invalidStoreInsertRequest = new StoreInsertRequest(
                null,
                "02-0000-0000",
                "서울시 어쩌구 00길 00",
                null,
                37.49082,
                127.033417
            );

            // then
            assertThatThrownBy(() -> storeService.insertStore(invalidStoreInsertRequest))
                .isInstanceOf(SikdorakRuntimeException.class);
        }
    }
}
