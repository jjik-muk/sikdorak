package com.jjikmuk.sikdorak.aws.controller;

import com.jjikmuk.sikdorak.aws.service.GeneratePresignedURLService;
import com.jjikmuk.sikdorak.aws.service.request.PresignedUrlCreateRequest;
import com.jjikmuk.sikdorak.aws.service.response.PresignedUrlCreateResponse;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.api.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images/url")
public class AwsS3Controller {

	private final GeneratePresignedURLService generatePresignedURLService;

	@UserOnly
	@PutMapping
	public CommonResponseEntity<PresignedUrlCreateResponse> createPresignedUrl(
		@RequestBody PresignedUrlCreateRequest presignedUrlCreateRequest,
		@AuthenticatedUser LoginUser loginUser) {
		PresignedUrlCreateResponse presignedUrlCreateResponse = generatePresignedURLService.createPresignedUrl(
			presignedUrlCreateRequest, loginUser);

		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.IMAGES_UPLOAD_PRESIGNED_URL_CREATE_SUCCESS,
			presignedUrlCreateResponse,
			HttpStatus.CREATED);
	}
}
