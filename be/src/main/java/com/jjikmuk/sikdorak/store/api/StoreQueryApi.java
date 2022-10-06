package com.jjikmuk.sikdorak.store.api;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_BY_RADIUS_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_DETAIL_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_SEARCH_SUCCESS;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.controller.CursorPageable;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.review.query.ReviewDao;
import com.jjikmuk.sikdorak.review.query.response.ReviewListResponse;
import com.jjikmuk.sikdorak.store.query.request.UserLocationInfoRequest;
import com.jjikmuk.sikdorak.store.query.response.StoreDetailResponse;
import com.jjikmuk.sikdorak.store.query.response.StoreListByRadiusResponse;
import com.jjikmuk.sikdorak.store.query.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.query.StoreDao;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreQueryApi {

	private final StoreDao storeDao;
	private final ReviewDao reviewDao;

	@GetMapping
	public CommonResponseEntity<List<StoreSearchResponse>> findStoresByStoreName(
		@RequestParam("storeName") String storeName) {
		List<StoreSearchResponse> findResponseList = storeDao.searchStoresByStoreNameContaining(
			storeName);

		return new CommonResponseEntity<>(STORE_SEARCH_SUCCESS, findResponseList, HttpStatus.OK);
	}

	@GetMapping(params = {"type=maps"})
	public CommonResponseEntity<StoreListByRadiusResponse> searchStoreByRadius(
		UserLocationInfoRequest userLocationInfoRequest,
		@CursorPageable CursorPageRequest cursorPageRequest) {

		StoreListByRadiusResponse stores = storeDao.searchStoresByRadius(
            userLocationInfoRequest, cursorPageRequest);

		return new CommonResponseEntity<>(STORE_SEARCH_BY_RADIUS_SUCCESS, stores, HttpStatus.OK);
	}

	@GetMapping("/{storeId}")
	public CommonResponseEntity<StoreDetailResponse> searchDetail(
		@PathVariable("storeId") Long storeId) {

		return new CommonResponseEntity<>(STORE_SEARCH_DETAIL_SUCCESS,
			storeDao.searchDetail(storeId), HttpStatus.OK);
	}

	@GetMapping("/{storeId}/reviews")
	public CommonResponseEntity<ReviewListResponse> searchStoreReviews(
		@PathVariable long storeId,
		@AuthenticatedUser LoginUser loginUser,
		@CursorPageable CursorPageRequest cursorPageRequest
	) {
		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.STORE_SEARCH_REVIEW_SUCCESS,
			reviewDao.searchReviewsByStoreId(storeId, loginUser, cursorPageRequest),
			HttpStatus.OK);
	}
}
