package com.jjikmuk.sikdorak.acceptance.review;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.fieldWithPathAndValidConstraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

interface ReviewSnippet {

	Snippet REVIEW_INSERT_REQUEST_SNIPPET = requestFields(
		fieldWithPathAndValidConstraints("reviewContent", ReviewInsertRequest.class)
			.type(JsonFieldType.STRING)
			.description("리뷰 내용"),
		fieldWithPathAndValidConstraints("storeId", ReviewInsertRequest.class)
			.type(JsonFieldType.NUMBER)
			.description("가게 아이디"),
		fieldWithPathAndValidConstraints("reviewScore", ReviewInsertRequest.class)
			.type(JsonFieldType.NUMBER)
			.description("리뷰 점수"),
		fieldWithPathAndValidConstraints("reviewVisibility", ReviewInsertRequest.class)
			.type(JsonFieldType.STRING)
			.description("리뷰 게시물의 공개 범위"),
		fieldWithPathAndValidConstraints("visitedDate", ReviewInsertRequest.class)
			.type(JsonFieldType.STRING)
			.description("가게 방문일"),
		fieldWithPathAndValidConstraints("tags", ReviewInsertRequest.class)
			.type(JsonFieldType.ARRAY)
			.description("리뷰를 표현하는 태그들"),
		fieldWithPathAndValidConstraints("images", ReviewInsertRequest.class)
			.type(JsonFieldType.ARRAY)
			.description("리뷰를 위한 사진 URL")
	);

	Snippet REVIEW_INSERT_RESPONSE_SNIPPET = responseFields(
		DEFAULT_CODE_RESPONSE_FIELD_DESCRIPTOR,
		DEFAULT_MESSAGE_RESPONSE_FIELD_DESCRIPTORS,
		fieldWithPath("data")
			.type(JsonFieldType.NULL)
			.description("리뷰 생성 데이터는 없습니다.")
	);

}
