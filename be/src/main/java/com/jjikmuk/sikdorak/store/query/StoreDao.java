package com.jjikmuk.sikdorak.store.query;

import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import com.jjikmuk.sikdorak.common.exception.InvalidPageParameterException;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.store.query.dto.UserLocationBasedMaxRange;
import com.jjikmuk.sikdorak.store.query.request.UserLocationInfoRequest;
import com.jjikmuk.sikdorak.store.query.response.StoreDetailResponse;
import com.jjikmuk.sikdorak.store.query.response.StoreListByRadiusResponse;
import com.jjikmuk.sikdorak.store.query.response.StoreRadiusSearchResponse;
import com.jjikmuk.sikdorak.store.query.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.command.domain.Store;
import com.jjikmuk.sikdorak.store.command.domain.StoreRepository;
import com.jjikmuk.sikdorak.store.command.domain.UserLocationInfo;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreDao {

	private static final int MAX_PAGE_SIZE = 20;

	private final StoreRepository storeRepository;
	private final ReviewRepository reviewRepository;

	@Transactional(readOnly = true)
	public StoreDetailResponse searchDetail(Long storeId) {
		Store store = storeRepository.findById(storeId)
			.orElseThrow(NotFoundStoreException::new);

		return new StoreDetailResponse(
			storeId,
			store.getStoreName(),
			store.getAddressName(),
			store.getRoadAddressName(),
			store.getContactNumber(),
			store.getX(),
			store.getY(),
			reviewRepository.countByStoreId(storeId),
			reviewRepository.findReviewScoreAverageByStoreId(storeId)
				.orElse(0.0)
		);
	}

	@Transactional(readOnly = true)
	public List<StoreSearchResponse> searchStoresByStoreNameContaining(String storeName) {
		if (storeName == null) {
			return Collections.emptyList();
		}

		return storeRepository.findStoresByStoreNameContaining(storeName)
			.stream()
			.map(StoreSearchResponse::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public StoreListByRadiusResponse searchStoresByRadius(
        UserLocationInfoRequest userLocationInfoRequest,
		CursorPageRequest cursorPageRequest) {

		validateCursorRequest(cursorPageRequest);
		UserLocationInfo userLocationInfo = new UserLocationInfo(userLocationInfoRequest.x(),
			userLocationInfoRequest.y(), userLocationInfoRequest.radius());
		UserLocationBasedMaxRange coordinates = new UserLocationBasedMaxRange(userLocationInfo);

		Slice<Store> stores = storeRepository.findStoresByRadius(coordinates.getMaxX(),
			coordinates.getMaxY(),
			coordinates.getMinX(),
			coordinates.getMinY(),
			cursorPageRequest.getAfter(),
			Pageable.ofSize(cursorPageRequest.getSize()));

		List<StoreRadiusSearchResponse> storeListResponse = getStoreListResponse(stores.getContent());
		CursorPageResponse cursorPageResponse = getCursorPageResponse(storeListResponse, stores.isLast());

		return StoreListByRadiusResponse.of(storeListResponse, cursorPageResponse);
	}

	private List<StoreRadiusSearchResponse> getStoreListResponse(List<Store> stores) {
		return stores.stream()
			.map(StoreRadiusSearchResponse::from)
			.toList();
	}

	private CursorPageResponse getCursorPageResponse(List<StoreRadiusSearchResponse> storeListResponse,
		boolean isLast) {

		long afterTargetId = storeListResponse.isEmpty() ? 0L
			: storeListResponse.get(storeListResponse.size() - 1).id();

		return new CursorPageResponse(storeListResponse.size(), 0L,
			afterTargetId, isLast);
	}

	private void validateCursorRequest(CursorPageRequest cursorPageRequest) {
		if (cursorPageRequest.getSize() > MAX_PAGE_SIZE) {
			throw new InvalidPageParameterException();
		}
	}

}
