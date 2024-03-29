package com.jjikmuk.sikdorak.integration.store;

import static org.assertj.core.api.Assertions.assertThat;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.store.query.request.UserLocationInfoRequest;
import com.jjikmuk.sikdorak.store.query.response.StoreListByRadiusResponse;
import com.jjikmuk.sikdorak.store.query.StoreDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Store 위치 기반 검색,조회")
class StoreSearchByRadiusIntegrationTest extends InitIntegrationTest {

    @Autowired
    private StoreDao storeDao;

    @Test
    @DisplayName("유저의 위치정보가 올바르다면 해당 반경 내의 가게목록을 페이징으로 반환한다")
    void search_by_radius_success() {
        long cursorPage = 0;
        int size = 5;
        CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
        UserLocationInfoRequest userLocationInfoRequest = new UserLocationInfoRequest(127.067, 37.6557, 1000);

        StoreListByRadiusResponse storeRadiusSearchResponses =
            storeDao.searchStoresByRadius(userLocationInfoRequest, cursorPageRequest);

        assertThat(storeRadiusSearchResponses.stores()).hasSize(3);
    }

    @Test
    @DisplayName("해당 반경 내에 해당하는 가게가 존재하지 않는다면 빈배열을 반환한다")
    void search_by_radius_empty_result() {
        long cursorPage = 0;
        int size = 5;
        CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, cursorPage, size, true);
        UserLocationInfoRequest userLocationInfoRequest = new UserLocationInfoRequest(127.067, 37.6557, 100);

        StoreListByRadiusResponse storeRadiusSearchResponses =
            storeDao.searchStoresByRadius(userLocationInfoRequest, cursorPageRequest);

        assertThat(storeRadiusSearchResponses.stores()).isEmpty();
    }

}
