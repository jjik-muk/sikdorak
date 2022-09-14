package com.jjikmuk.sikdorak.store.controller.response;

import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import java.util.List;

public record StoreListByRadiusResponse(

    List<StoreRadiusSearchResponse> stores,
    CursorPageResponse page

) {

    public static StoreListByRadiusResponse of(List<StoreRadiusSearchResponse> stores,
        CursorPageResponse page) {
        return new StoreListByRadiusResponse(stores, page);
    }
}
