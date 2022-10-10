package com.jjikmuk.sikdorak.store.api;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_CREATE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_MODIFY_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_REMOVE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.STORE_VERIFY_OR_SAVE_RESPONSE;

import com.jjikmuk.sikdorak.common.aop.AdminOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.store.command.app.StoreService;
import com.jjikmuk.sikdorak.store.command.app.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.command.app.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.command.app.request.StoreVerifyOrSaveRequest;
import com.jjikmuk.sikdorak.store.command.app.response.StoreVerifyOrSaveResponse;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.api.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreCommandApi {

	private final StoreService storeService;

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

}
