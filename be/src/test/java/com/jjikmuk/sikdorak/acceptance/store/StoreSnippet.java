package com.jjikmuk.sikdorak.acceptance.store;

import com.jjikmuk.sikdorak.store.controller.response.StoreFindResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonListResponseFieldsWithValidConstraints;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonResponseNonFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

public interface StoreSnippet {

	Snippet STORE_FIND_REQUEST = requestParameters(
			parameterWithName("storeName").description("가게 이름 검색 키워드")
	);

	Snippet STORE_FIND_RESPONSE = commonListResponseFieldsWithValidConstraints(
			StoreFindResponse.class,
			 fieldWithPath("id").type(JsonFieldType.NUMBER).description(Constants.ID_DESCRIPTION),
			fieldWithPath("storeName").type(JsonFieldType.STRING).description(Constants.STORENAME_DESCRIPTION),
			fieldWithPath("contactNumber").type(JsonFieldType.STRING).description(Constants.CONTACTNUMBER_DESCRIPTION),
			fieldWithPath("baseAddress").type(JsonFieldType.STRING).description(Constants.BASEADDRESS_DESCRIPTION),
			fieldWithPath("detailAddress").type(JsonFieldType.STRING).description(Constants.DETAILADDRESS_DESCRIPTION).optional(),
			fieldWithPath("latitude").type(JsonFieldType.NUMBER).description(Constants.LATITUDE_DESCRIPTION),
			fieldWithPath("longitude").type(JsonFieldType.NUMBER).description(Constants.LONGITUDE_DESCRIPTION)
	);

	Snippet STORE_INSERT_REQUEST_SNIPPET = requestFields(
		fieldWithPath("storeName").type(JsonFieldType.STRING).description(Constants.STORENAME_DESCRIPTION),
		fieldWithPath("contactNumber").type(JsonFieldType.STRING).description(Constants.CONTACTNUMBER_DESCRIPTION),
		fieldWithPath("baseAddress").type(JsonFieldType.STRING).description(Constants.BASEADDRESS_DESCRIPTION),
		fieldWithPath("detailAddress").type(JsonFieldType.STRING).description(Constants.DETAILADDRESS_DESCRIPTION).optional(),
		fieldWithPath("latitude").type(JsonFieldType.NUMBER).description(Constants.LATITUDE_DESCRIPTION),
		fieldWithPath("longitude").type(JsonFieldType.NUMBER).description(Constants.LONGITUDE_DESCRIPTION)
	);

	Snippet STORE_INSERT_RESPONSE_SNIPPET = commonResponseNonFields();

	class Constants {
		private static final String ID_DESCRIPTION = "가게 아이디";
		private static final String STORENAME_DESCRIPTION = "가게 이름";
		private static final String CONTACTNUMBER_DESCRIPTION = "가게 연락처";
		private static final String BASEADDRESS_DESCRIPTION = "주소";
		private static final String DETAILADDRESS_DESCRIPTION = "상세 주소";
		private static final String LATITUDE_DESCRIPTION = "위도";
		private static final String LONGITUDE_DESCRIPTION = "경도";
	}
}
