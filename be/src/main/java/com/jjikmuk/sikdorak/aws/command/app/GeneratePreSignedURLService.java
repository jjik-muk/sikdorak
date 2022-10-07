package com.jjikmuk.sikdorak.aws.command.app;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.jjikmuk.sikdorak.aws.command.app.request.PreSignedUrlCreateRequest;
import com.jjikmuk.sikdorak.aws.command.app.response.PreSignedUrlCreateResponse;
import com.jjikmuk.sikdorak.aws.command.domain.ImageExtension;
import com.jjikmuk.sikdorak.common.properties.AwsProperties;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneratePreSignedURLService {

	public static final int FIVE_MINUTE = 1000 * 60 * 5;

	private final AmazonS3 amazonS3;
	private final AwsProperties awsProperties;
	private final UserRepository userRepository;

	/**
	 * 이미지 파일 확장자에 따른 s3 저장할 수 있는 PUT presignedURL을 리턴합니다. 현재는 유저에 따른 이미지 폴더 경로로 저장하지 않습니다.
	 *
	 * @param presignedUrlCreateRequest 이미지 파일 확장자를 담고있는 요청 객체
	 * @param loginUser 로그인 유저 정보
	 * @return presignedURL를 담고있는 응답 객체
	 */
	public PreSignedUrlCreateResponse createPutPreSignedUrl(
		PreSignedUrlCreateRequest presignedUrlCreateRequest, LoginUser loginUser) {
		userRepository.findById(loginUser.getId())
			.orElseThrow(NotFoundUserException::new);

		// Generate the presigned URL.
		String fileName = generateRandomImageFileName(presignedUrlCreateRequest);
		URL url = generatePutPreSignedUrl(fileName);

		return new PreSignedUrlCreateResponse(url.toString(), fileName);
	}

	private URL generatePutPreSignedUrl(String fileName) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest =
			new GeneratePresignedUrlRequest(awsProperties.getBucket(), "origin/" + fileName)
				.withMethod(HttpMethod.PUT)
				.withExpiration(getExpiration(FIVE_MINUTE));
		return amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
	}

	private String generateRandomImageFileName(
		PreSignedUrlCreateRequest presignedUrlCreateRequest) {
		ImageExtension imageExtension = ImageExtension.create(
			presignedUrlCreateRequest.getExtension());
		return UUID.randomUUID() + imageExtension.getExtension();
	}

	private Date getExpiration(long expirationAsMilliseconds) {
		java.util.Date expiration = new java.util.Date();
		long expTimeMillis = Instant.now().toEpochMilli();
		expTimeMillis += expirationAsMilliseconds;
		expiration.setTime(expTimeMillis);
		return expiration;
	}
}
