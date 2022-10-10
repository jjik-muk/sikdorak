package com.jjikmuk.sikdorak.documentationtest.image;

import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfCommon;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfObjectWithConstraintsAndFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.jjikmuk.sikdorak.image.command.app.request.PreSignedUrlCreateRequest;
import com.jjikmuk.sikdorak.image.command.app.response.PreSignedUrlCreateResponse;
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
			fieldWithPath("preSignedUrl").type(JsonFieldType.STRING).description("PUT S3 presigned URL"),
			fieldWithPath("fileName").type(JsonFieldType.STRING).description("S3에 저장될 파일 이름"))
	);

}
