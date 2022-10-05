package com.jjikmuk.sikdorak.documentationtest.aws;

import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfCommon;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfObjectWithConstraintsAndFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.jjikmuk.sikdorak.aws.service.request.PreSignedUrlCreateRequest;
import com.jjikmuk.sikdorak.aws.service.response.PreSignedUrlCreateResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface S3Snippet {

	Snippet PRESIGNED_URL_CREATE_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		PreSignedUrlCreateRequest.class,
		fieldWithPath("extension").type(JsonFieldType.STRING).description("이미지 파일 확장자")
	);

	Snippet PRESIGNED_URL_CREATE_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfObjectWithConstraintsAndFields(PreSignedUrlCreateResponse.class,
			fieldWithPath("presignedUrl").type(JsonFieldType.STRING).description("PUT S3 presigned URL"))

	);

}
