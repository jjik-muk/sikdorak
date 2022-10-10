package com.jjikmuk.sikdorak.image.api;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.image.command.app.AwsS3ImageService;
import com.jjikmuk.sikdorak.image.command.app.ImageMetaDataService;
import com.jjikmuk.sikdorak.image.command.app.request.PreSignedUrlCreateRequest;
import com.jjikmuk.sikdorak.image.command.app.response.PreSignedUrlCreateResponse;
import com.jjikmuk.sikdorak.user.auth.api.AuthenticatedUser;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/images/url")
public class AwsCommandApi {

	private final AwsS3ImageService generatePresignedURLService;
	private final ImageMetaDataService imageMetaDataService;

	@UserOnly
	@PutMapping
	public CommonResponseEntity<PreSignedUrlCreateResponse> createPresignedUrl(
		@RequestBody PreSignedUrlCreateRequest presignedUrlCreateRequest,
		@AuthenticatedUser LoginUser loginUser) {
		PreSignedUrlCreateResponse presignedUrlCreateResponse = generatePresignedURLService.createPutPreSignedUrl(
			presignedUrlCreateRequest, loginUser);
		imageMetaDataService.initImageMetaData(presignedUrlCreateResponse.fileName(), loginUser);

		return new CommonResponseEntity<>(
			ResponseCodeAndMessages.IMAGES_UPLOAD_PRESIGNED_URL_CREATE_SUCCESS,
			presignedUrlCreateResponse,
			HttpStatus.CREATED);
	}
}
