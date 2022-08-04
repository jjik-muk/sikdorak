package com.jjikmuk.sikdorak.store.controller;

import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.store.controller.response.StoreFindResponse;
import com.jjikmuk.sikdorak.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_FIND_SUCCESS;

@RestController
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @GetMapping("api/stores")
    public CommonResponseEntity<List<StoreFindResponse>> findStore(@RequestParam("storeName") String storeName ) {
        List<StoreFindResponse> findResponseList = storeService.findStoresByStoreNameContaining(storeName);

        return new CommonResponseEntity<>(STORE_FIND_SUCCESS, findResponseList, HttpStatus.OK);
    }

}
