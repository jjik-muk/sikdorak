package com.jjikmuk.sikdorak.store.controller;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_CREATE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_MODIFY_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_REMOVE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_BY_RADIUS_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_SUCCESS;

import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.store.controller.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreRadiusSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.service.StoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PutMapping("/{storeId}")
	public CommonResponseEntity<Void> modifyStore(@PathVariable("storeId") Long storeId, @RequestBody StoreModifyRequest modifyRequest) {
		storeService.modifyStore(storeId, modifyRequest);

		return new CommonResponseEntity<>(STORE_MODIFY_SUCCESS, HttpStatus.OK);
	}

	@DeleteMapping("/{storeId}")
	public CommonResponseEntity<Void> removeStore(@PathVariable("storeId") Long storeId) {
		storeService.removeStore(storeId);

		return new CommonResponseEntity<>(STORE_REMOVE_SUCCESS, HttpStatus.OK);
	}

	@GetMapping(params = {"type=maps"})
	public CommonResponseEntity<List<StoreRadiusSearchResponse>> searchStoreByRadius(
		@RequestParam("x") double x,
		@RequestParam("y") double y,
		@RequestParam("radius") int radius
		) {

		List<StoreRadiusSearchResponse> stores = storeService.searchStoresByRadius(x, y, radius);

		return new CommonResponseEntity<>(STORE_SEARCH_BY_RADIUS_SUCCESS, stores,HttpStatus.OK);
	}
}
