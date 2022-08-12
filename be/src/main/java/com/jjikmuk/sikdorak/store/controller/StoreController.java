package com.jjikmuk.sikdorak.store.controller;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_INSERT_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_SUCCESS;

import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.store.controller.request.StoreInsertRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/stores")
public class StoreController {

	private final StoreService storeService;

	@GetMapping()
	public CommonResponseEntity<List<StoreSearchResponse>> findStoresByStoreName(
		@RequestParam("storeName") String storeName) {
		List<StoreSearchResponse> findResponseList = storeService.searchStoresByStoreNameContaining(
			storeName);

		return new CommonResponseEntity<>(STORE_SEARCH_SUCCESS, findResponseList, HttpStatus.OK);
	}

	@PostMapping()
	public CommonResponseEntity<Void> insertStor(@RequestBody StoreInsertRequest insertRequest) {
		storeService.insertStore(insertRequest);

		return new CommonResponseEntity<>(STORE_INSERT_SUCCESS, null, HttpStatus.CREATED);
	}
}