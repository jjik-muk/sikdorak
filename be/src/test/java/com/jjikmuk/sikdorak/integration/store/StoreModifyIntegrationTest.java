package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.controller.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;
import com.jjikmuk.sikdorak.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("StoreModify 통합테스트")
public class StoreModifyIntegrationTest extends InitIntegrationTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @Nested
    @DisplayName("가게를 수정할 때")
    class ModifyStoreTest {

        @Test
        @DisplayName("존재하는 가게에 대한 수정 요청이라면 가게를 수정할 수 있다.")
        void modify_store_success() {
            // given
            Store savedStore = testData.store;

            StoreModifyRequest storeModifyRequest = new StoreModifyRequest(
                savedStore.getId(),
                "업데이트된 가게 이름",
                "02-9999-9999",
                "서울시 어쩌구 00길 00",
                null,
                37.49082,
                127.033417
            );

            // when
            Long savedStoreId = storeService.modifyStore(storeModifyRequest);

            // then
            Store updatedStore = storeRepository.findById(savedStoreId).orElseThrow();
            assertThat(storeModifyRequest.getId()).isEqualTo(updatedStore.getId());
            assertThat(storeModifyRequest.getStoreName()).isEqualTo(updatedStore.getStoreName());
            assertThat(storeModifyRequest.getContactNumber()).isEqualTo(updatedStore.getContactNumber());
            assertThat(storeModifyRequest.getBaseAddress()).isEqualTo(updatedStore.getBaseAddress());
            assertThat(storeModifyRequest.getDetailAddress()).isEqualTo(updatedStore.getDetailAddress());
            assertThat(storeModifyRequest.getLatitude()).isEqualTo(updatedStore.getLatitude());
            assertThat(storeModifyRequest.getLongitude()).isEqualTo(updatedStore.getLongitude());
        }

        @Test
        @DisplayName("존재하지 않는 가게에 대한 수정 요청이라면 예외를 발생시킨다.")
        void create_store_failed() {
            long notSavedStoreId = Long.MIN_VALUE;

            StoreModifyRequest storeModifyRequest = new StoreModifyRequest(
                notSavedStoreId,
                "업데이트된 가게 이름",
                "02-9999-9999",
                "서울시 어쩌구 00길 00",
                null,
                37.49082,
                127.033417
            );

            // then
            assertThatThrownBy(() -> storeService.modifyStore(storeModifyRequest))
                .isInstanceOf(SikdorakRuntimeException.class);
        }
    }
}
