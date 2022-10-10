package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.command.domain.Store;
import com.jjikmuk.sikdorak.store.command.domain.StoreRepository;
import com.jjikmuk.sikdorak.store.command.app.StoreService;
import com.jjikmuk.sikdorak.store.command.app.request.StoreModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Store 수정 기능")
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
            Long savedStoreId = testData.store.getId();

            StoreModifyRequest storeModifyRequest = new StoreModifyRequest(
                "업데이트된 가게 이름",
                "02-9999-9999",
                "서울시 어쩌구 11-22",
                "서울시 어쩌구 어떤길",
                127.033417,
                37.49082
            );

            // when
            Long updatedStoreId = storeService.modifyStore(savedStoreId, storeModifyRequest);

            // then
            Store updatedStore = storeRepository.findById(updatedStoreId).orElseThrow();
            assertThat(storeModifyRequest.getStoreName()).isEqualTo(updatedStore.getStoreName());
            assertThat(storeModifyRequest.getContactNumber()).isEqualTo(updatedStore.getContactNumber());
            assertThat(storeModifyRequest.getAddressName()).isEqualTo(updatedStore.getAddressName());
            assertThat(storeModifyRequest.getRoadAddressName()).isEqualTo(updatedStore.getRoadAddressName());
            assertThat(storeModifyRequest.getY()).isEqualTo(updatedStore.getY());
            assertThat(storeModifyRequest.getX()).isEqualTo(updatedStore.getX());
        }

        @Test
        @DisplayName("존재하지 않는 가게에 대한 수정 요청이라면 예외를 발생시킨다.")
        void create_store_failed() {
            long notSavedStoreId = Long.MIN_VALUE;

            StoreModifyRequest storeModifyRequest = new StoreModifyRequest(
                "업데이트된 가게 이름",
                "02-9999-9999",
                "서울시 어쩌구 11-22",
                "서울시 어쩌구 어떤길",
                37.49082,
                127.033417
            );

            // then
            assertThatThrownBy(() -> storeService.modifyStore(notSavedStoreId, storeModifyRequest))
                .isInstanceOf(SikdorakRuntimeException.class);
        }
    }
}
