package com.jjikmuk.sikdorak.documentationtest.aws;

import static com.jjikmuk.sikdorak.documentationtest.aws.S3Snippet.PRESIGNED_URL_CREATE_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.aws.S3Snippet.PRESIGNED_URL_CREATE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.aws.controller.request.PresignedUrlCreateRequest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class PresignedURLCreateDocumentationTest extends InitDocumentationTest {

	@Test
	@DisplayName("PUT S3 presigned URL 생성 요청이 정상적인 경우라면 URL 생성 후 정상 상태 코드와 URL을 반환한다")
	void create_presignedURL_success() {
		PresignedUrlCreateRequest presignedUrlCreateRequest = new PresignedUrlCreateRequest("jpg");

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				PRESIGNED_URL_CREATE_REQUEST_SNIPPET,
				PRESIGNED_URL_CREATE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.user1ValidAuthorizationHeader)
			.body(presignedUrlCreateRequest)

		.when()
			.put("/api/images/url")

		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("code", Matchers.equalTo(ResponseCodeAndMessages.IMAGES_UPLOAD_PRESIGNED_URL_CREATE_SUCCESS.getCode()))
			.body("message", Matchers.equalTo(ResponseCodeAndMessages.IMAGES_UPLOAD_PRESIGNED_URL_CREATE_SUCCESS.getMessage()));
	}
}
