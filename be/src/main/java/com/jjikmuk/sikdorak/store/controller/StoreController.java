package com.jjikmuk.sikdorak.store.controller;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_CREATE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_MODIFY_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_SUCCESS;

import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.store.controller.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public CommonResponseEntity<Void> createStore(@RequestBody StoreCreateRequest createRequest) {
		storeService.createStore(createRequest);

		return new CommonResponseEntity<>(STORE_CREATE_SUCCESS, null, HttpStatus.CREATED);
	}

	@PutMapping()
	public CommonResponseEntity<Void> modifyStore(@RequestBody StoreModifyRequest modifyRequest) {
		storeService.modifyStore(modifyRequest);

		return new CommonResponseEntity<>(STORE_MODIFY_SUCCESS, HttpStatus.OK);
	}
}
