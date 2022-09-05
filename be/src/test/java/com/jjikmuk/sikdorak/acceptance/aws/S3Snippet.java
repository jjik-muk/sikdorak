package com.jjikmuk.sikdorak.acceptance.aws;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfCommon;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfObjectWithConstraintsAndFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.jjikmuk.sikdorak.aws.controller.request.PresignedUrlCreateRequest;
import com.jjikmuk.sikdorak.aws.controller.response.PresignedUrlCreateResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface S3Snippet {

	Snippet PRESIGNED_URL_CREATE_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		PresignedUrlCreateRequest.class,
		fieldWithPath("extension").type(JsonFieldType.STRING).description("이미지 파일 확장자")
	);

	Snippet PRESIGNED_URL_CREATE_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfObjectWithConstraintsAndFields(PresignedUrlCreateResponse.class,
			fieldWithPath("presignedUrl").type(JsonFieldType.STRING).description("PUT S3 presigned URL"))

	);

}
