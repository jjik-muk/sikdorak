package com.jjikmuk.sikdorak.acceptance.store;

import com.jjikmuk.sikdorak.store.controller.response.StoreFindResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonListResponseFieldsWithValidConstraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

public interface StoreSnippet {

	Snippet STORE_FIND_REQUEST = requestParameters(
			parameterWithName("storeName").description("가게 이름 검색 키워드")
	);

	Snippet STORE_FIND_RESPONSE = commonListResponseFieldsWithValidConstraints(
			StoreFindResponse.class,
			fieldWithPath("id").type(JsonFieldType.NUMBER).description("가게 아이디"),
			fieldWithPath("storeName").type(JsonFieldType.STRING).description("가게 이름"),
			fieldWithPath("contactNumber").type(JsonFieldType.STRING).description("가게 연락처"),
			fieldWithPath("baseAddress").type(JsonFieldType.STRING).description("주소"),
			fieldWithPath("detailAddress").type(JsonFieldType.STRING).description("상세 주소").optional(),
			fieldWithPath("latitude").type(JsonFieldType.NUMBER).description("위도"),
			fieldWithPath("longitude").type(JsonFieldType.NUMBER).description("경도")
	);
}
