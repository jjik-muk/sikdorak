package com.jjikmuk.sikdorak.aws.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.jjikmuk.sikdorak.aws.controller.request.PresignedUrlCreateRequest;
import com.jjikmuk.sikdorak.aws.controller.response.PresignedUrlCreateResponse;
import com.jjikmuk.sikdorak.common.properties.AwsProperties;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GeneratePresignedURLService {

	public static final int FIVE_MINUTE = 1000 * 60 * 5;

	private final AmazonS3 amazonS3;
	private final AwsProperties awsProperties;

	/**
	 * 이미지 파일 확장자에 따른 s3 저장할 수 있는 presignedURL을 리턴합니다.
	 * 현재는 유저에 따른 이미지 폴더 경로로 저장하지 않습니다.
	 * @param presignedUrlCreateRequest 이미지 파일 확장자를 담고있는 요청 객체
	 * @param loginUser (현재 사용하지 않습니다.)
	 * @return presignedURL를 담고있는 응답 객체
	 */
	@Transactional(readOnly = true)
	public PresignedUrlCreateResponse createPresignedUrl(PresignedUrlCreateRequest presignedUrlCreateRequest, LoginUser loginUser) {
		ImageExtension imageExtension = ImageExtension.create(
			presignedUrlCreateRequest.getExtension());
		String fileName = UUID.randomUUID() + imageExtension.getExtension();

		// Generate the presigned URL.
		GeneratePresignedUrlRequest generatePresignedUrlRequest =
			new GeneratePresignedUrlRequest(awsProperties.getBucket(), "origin/" + fileName)
				.withMethod(HttpMethod.PUT)
				.withExpiration(getExpiration(FIVE_MINUTE));
		URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);

		return new PresignedUrlCreateResponse(url.toString());
	}

	private static Date getExpiration(long expirationAsMilliseconds) {
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = Instant.now().toEpochMilli();
		expTimeMillis += expirationAsMilliseconds;
		expiration.setTime(expTimeMillis);
		return expiration;
	}
}
