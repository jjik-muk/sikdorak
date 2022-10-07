package com.jjikmuk.sikdorak.aws.controller;

import com.jjikmuk.sikdorak.aws.service.GeneratePreSignedURLService;
import com.jjikmuk.sikdorak.aws.service.ImageMetaDataService;
import com.jjikmuk.sikdorak.aws.service.request.PreSignedUrlCreateRequest;
import com.jjikmuk.sikdorak.aws.service.response.PreSignedUrlCreateResponse;
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

	private final GeneratePreSignedURLService generatePresignedURLService;
	private final ImageMetaDataService imageMetaDataService;

	@UserOnly
	@PutMapping
	public CommonResponseEntity<PreSignedUrlCreateResponse> createPresignedUrl(
		@RequestBody PreSignedUrlCreateRequest presignedUrlCreateRequest,
		@AuthenticatedUser LoginUser loginUser) {
		PreSignedUrlCreateResponse presignedUrlCreateResponse = generatePresignedURLService.createPreSignedUrl(
			presignedUrlCreateRequest, loginUser);
		imageMetaDataService.initImageMetaData(presignedUrlCreateResponse.fileName(), loginUser);

		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.IMAGES_UPLOAD_PRESIGNED_URL_CREATE_SUCCESS,
			presignedUrlCreateResponse,
			HttpStatus.CREATED);
	}
}
