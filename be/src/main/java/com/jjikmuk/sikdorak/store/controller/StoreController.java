package com.jjikmuk.sikdorak.store.controller;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_CREATE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_MODIFY_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_REMOVE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_BY_RADIUS_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_DETAIL_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_VERIFY_OR_SAVE_RESPONSE;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.AdminOnly;
import com.jjikmuk.sikdorak.common.controller.CursorPageable;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.review.service.ReviewService;
import com.jjikmuk.sikdorak.review.service.response.ReviewListResponse;
import com.jjikmuk.sikdorak.store.service.StoreService;
import com.jjikmuk.sikdorak.store.service.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.service.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.service.request.StoreVerifyOrSaveRequest;
import com.jjikmuk.sikdorak.store.service.request.UserLocationInfoRequest;
import com.jjikmuk.sikdorak.store.service.response.StoreDetailResponse;
import com.jjikmuk.sikdorak.store.service.response.StoreListByRadiusResponse;
import com.jjikmuk.sikdorak.store.service.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.service.response.StoreVerifyOrSaveResponse;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
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
@RequestMapping("/api/stores")
public class StoreController {

	private final StoreService storeService;
	private final ReviewService reviewService;

	@GetMapping
	public CommonResponseEntity<List<StoreSearchResponse>> findStoresByStoreName(
		@RequestParam("storeName") String storeName) {
		List<StoreSearchResponse> findResponseList = storeService.searchStoresByStoreNameContaining(
			storeName);

		return new CommonResponseEntity<>(STORE_SEARCH_SUCCESS, findResponseList, HttpStatus.OK);
	}

	@AdminOnly
	@PostMapping
	public CommonResponseEntity<Void> createStore(
		@RequestBody StoreCreateRequest createRequest,
		@AuthenticatedUser LoginUser loginUser) {
		storeService.createStore(createRequest);

		return new CommonResponseEntity<>(STORE_CREATE_SUCCESS, null, HttpStatus.CREATED);
	}

	@PutMapping
	public CommonResponseEntity<StoreVerifyOrSaveResponse> verifyOrSaveStore(
		@RequestBody StoreVerifyOrSaveRequest verifyOrSaveRequest) {

		return new CommonResponseEntity<>(STORE_VERIFY_OR_SAVE_RESPONSE,
			storeService.verifyOrSave(verifyOrSaveRequest), HttpStatus.OK);
	}

	@AdminOnly
	@PutMapping("/{storeId}")
	public CommonResponseEntity<Void> modifyStore(@PathVariable("storeId") Long storeId,
		@RequestBody StoreModifyRequest modifyRequest,
		@AuthenticatedUser LoginUser loginUser) {
		storeService.modifyStore(storeId, modifyRequest);

		return new CommonResponseEntity<>(STORE_MODIFY_SUCCESS, HttpStatus.OK);
	}

	@AdminOnly
	@DeleteMapping("/{storeId}")
	public CommonResponseEntity<Void> removeStore(
		@PathVariable("storeId") Long storeId,
		@AuthenticatedUser LoginUser loginUser) {
		storeService.removeStore(storeId);

		return new CommonResponseEntity<>(STORE_REMOVE_SUCCESS, HttpStatus.OK);
	}

	@GetMapping(params = {"type=maps"})
	public CommonResponseEntity<StoreListByRadiusResponse> searchStoreByRadius(
		UserLocationInfoRequest userLocationInfoRequest,
		@CursorPageable CursorPageRequest cursorPageRequest) {

		StoreListByRadiusResponse stores = storeService.searchStoresByRadius(
            userLocationInfoRequest, cursorPageRequest);

		return new CommonResponseEntity<>(STORE_SEARCH_BY_RADIUS_SUCCESS, stores, HttpStatus.OK);
	}

	@GetMapping("/{storeId}")
	public CommonResponseEntity<StoreDetailResponse> searchDetail(
		@PathVariable("storeId") Long storeId) {

		return new CommonResponseEntity<>(STORE_SEARCH_DETAIL_SUCCESS,
			storeService.searchDetail(storeId), HttpStatus.OK);
	}

	@GetMapping("/{storeId}/reviews")
	public CommonResponseEntity<ReviewListResponse> searchStoreReviews(
		@PathVariable long storeId,
		@AuthenticatedUser LoginUser loginUser,
		@CursorPageable CursorPageRequest cursorPageRequest
	) {
		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.STORE_SEARCH_REVIEW_SUCCESS,
			reviewService.searchReviewsByStoreId(storeId, loginUser, cursorPageRequest),
			HttpStatus.OK);
	}
}
