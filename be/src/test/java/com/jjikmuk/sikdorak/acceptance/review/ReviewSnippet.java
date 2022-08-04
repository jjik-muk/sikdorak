package com.jjikmuk.sikdorak.acceptance.review;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonRequestFieldsWithValidConstraints;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonResponseNonFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

interface ReviewSnippet {

	Snippet REVIEW_INSERT_REQUEST_SNIPPET = commonRequestFieldsWithValidConstraints(
		ReviewInsertRequest.class,
		fieldWithPath("reviewContent")
			.type(JsonFieldType.STRING)
			.description("리뷰 내용"),
		fieldWithPath("storeId")
			.type(JsonFieldType.NUMBER)
			.description("가게 아이디"),
		fieldWithPath("reviewScore")
			.type(JsonFieldType.NUMBER)
			.description("리뷰 점수"),
		fieldWithPath("reviewVisibility")
			.type(JsonFieldType.STRING)
			.description("리뷰 게시물의 공개 범위"),
		fieldWithPath("visitedDate")
			.type(JsonFieldType.STRING)
			.description("가게 방문일"),
		fieldWithPath("tags")
			.type(JsonFieldType.ARRAY)
			.description("리뷰를 표현하는 태그들"),
		fieldWithPath("images")
			.type(JsonFieldType.ARRAY)
			.description("리뷰를 위한 사진 URL")
	);

	Snippet REVIEW_INSERT_RESPONSE_SNIPPET = commonResponseNonFields();
}
