package com.jjikmuk.sikdorak.acceptance.store;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfCommon;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfCommonNonData;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfListWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfObjectWithConstraintsAndFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import com.jjikmuk.sikdorak.store.controller.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreVerifyAndUpsertRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreRadiusSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreVerifyAndUpsertResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface StoreSnippet {

	// 가게 이름으로 조회
	Snippet STORE_SEARCH_REQUEST = requestParameters(
		parameterWithName("storeName").description("가게 이름 검색 키워드")
	);

	Snippet STORE_SEARCH_RESPONSE = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfListWithConstraintsAndFields(
			StoreSearchResponse.class,
			fieldWithPath("id").type(JsonFieldType.NUMBER).description(Constants.ID_DESCRIPTION),
			fieldWithPath("storeName").type(JsonFieldType.STRING)
				.description(Constants.STORENAME_DESCRIPTION),
			fieldWithPath("contactNumber").type(JsonFieldType.STRING)
				.description(Constants.CONTACTNUMBER_DESCRIPTION),
			fieldWithPath("addressName").type(JsonFieldType.STRING)
				.description(Constants.ADDRESS_NAME_DESCRIPTION),
			fieldWithPath("roadAddressName").type(JsonFieldType.STRING)
				.description(Constants.ROADADDRESS_NAME_DESCRIPTION).optional(),
			fieldWithPath("y").type(JsonFieldType.NUMBER)
				.description(Constants.Y_DESCRIPTION),
			fieldWithPath("x").type(JsonFieldType.NUMBER)
				.description(Constants.X_DESCRIPTION)
		)
	);

	// 가게 생성
	Snippet STORE_CREATE_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		StoreCreateRequest.class,
		fieldWithPath("storeName").type(JsonFieldType.STRING)
			.description(Constants.STORENAME_DESCRIPTION),
		fieldWithPath("contactNumber").type(JsonFieldType.STRING)
			.description(Constants.CONTACTNUMBER_DESCRIPTION),
		fieldWithPath("addressName").type(JsonFieldType.STRING)
			.description(Constants.ADDRESS_NAME_DESCRIPTION),
		fieldWithPath("roadAddressName").type(JsonFieldType.STRING)
			.description(Constants.ROADADDRESS_NAME_DESCRIPTION).optional(),
		fieldWithPath("y").type(JsonFieldType.NUMBER)
			.description(Constants.Y_DESCRIPTION),
		fieldWithPath("x").type(JsonFieldType.NUMBER)
			.description(Constants.X_DESCRIPTION)
	);

	Snippet STORE_CREATE_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommonNonData());

	// 가게 수정
	Snippet STORE_MODIFY_REQUEST_PARAM_SNIPPET = pathParameters(
		parameterWithName("storeId").description(Constants.ID_DESCRIPTION)
	);
	
	Snippet STORE_MODIFY_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		StoreModifyRequest.class,
		fieldWithPath("storeName").type(JsonFieldType.STRING)
			.description(Constants.STORENAME_DESCRIPTION),
		fieldWithPath("contactNumber").type(JsonFieldType.STRING)
			.description(Constants.CONTACTNUMBER_DESCRIPTION),
		fieldWithPath("addressName").type(JsonFieldType.STRING)
			.description(Constants.ADDRESS_NAME_DESCRIPTION),
		fieldWithPath("roadAddressName").type(JsonFieldType.STRING)
			.description(Constants.ROADADDRESS_NAME_DESCRIPTION).optional(),
		fieldWithPath("y").type(JsonFieldType.NUMBER)
			.description(Constants.Y_DESCRIPTION),
		fieldWithPath("x").type(JsonFieldType.NUMBER)
			.description(Constants.X_DESCRIPTION)
	);

	Snippet STORE_MODIFY_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommonNonData());

	// 가게 삭제
	Snippet STORE_REMOVE_REQUEST_PARAM_SNIPPET = pathParameters(
		parameterWithName("storeId").description(Constants.ID_DESCRIPTION)
	);

	Snippet STORE_REMOVE_RESPONSE_SNIPPET =  createResponseSnippetWithFields(
		responseFieldsOfCommonNonData());

	// 근처 가게 조회
	Snippet STORE_SEARCH_BY_RADIUS_REQUEST_SNIPPET = requestParameters(
		parameterWithName("type").description(Constants.REQUEST_PAGE_TYPE_DESCRIPTION),
		parameterWithName("x").description(Constants.X_DESCRIPTION),
		parameterWithName("y").description(Constants.Y_DESCRIPTION),
		parameterWithName("radius").description(Constants.RADIUS_DESCRIPTION)
	);
	
	Snippet STORE_SEARCH_BY_RADIUS_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfListWithConstraintsAndFields(
			StoreRadiusSearchResponse.class,
			fieldWithPath("id").type(JsonFieldType.NUMBER).description(Constants.ID_DESCRIPTION),
			fieldWithPath("storeName").type(JsonFieldType.STRING)
				.description(Constants.STORENAME_DESCRIPTION),
			fieldWithPath("contactNumber").type(JsonFieldType.STRING)
				.description(Constants.CONTACTNUMBER_DESCRIPTION),
			fieldWithPath("addressName").type(JsonFieldType.STRING)
				.description(Constants.ADDRESS_NAME_DESCRIPTION),
			fieldWithPath("roadAddressName").type(JsonFieldType.STRING)
				.description(Constants.ROADADDRESS_NAME_DESCRIPTION).optional(),
			fieldWithPath("x").type(JsonFieldType.NUMBER)
				.description(Constants.X_DESCRIPTION),
			fieldWithPath("y").type(JsonFieldType.NUMBER)
				.description(Constants.Y_DESCRIPTION)
		)
	);

	// 가게 등록 검증
	Snippet STORE_VERIFY_AND_UPSERT_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		StoreVerifyAndUpsertRequest.class,
		fieldWithPath("kakaoPlaceId").type(JsonFieldType.NUMBER)
			.description(Constants.CONTACTNUMBER_DESCRIPTION),
		fieldWithPath("storeName").type(JsonFieldType.STRING)
			.description(Constants.STORENAME_DESCRIPTION),
		fieldWithPath("y").type(JsonFieldType.NUMBER)
			.description(Constants.Y_DESCRIPTION),
		fieldWithPath("x").type(JsonFieldType.NUMBER)
			.description(Constants.X_DESCRIPTION)
	);

	Snippet STORE_VERIFY_AND_UPSERT_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),
		
		responseFieldsOfObjectWithConstraintsAndFields(
			StoreVerifyAndUpsertResponse.class,
			fieldWithPath("storeId").type(JsonFieldType.NUMBER)
				.description(Constants.ID_DESCRIPTION),
			fieldWithPath("kakaoPlaceId").type(JsonFieldType.NUMBER)
				.description(Constants.KAKAO_PLACE_ID_DESCRIPTION),
			fieldWithPath("storeName").type(JsonFieldType.STRING)
				.description(Constants.STORENAME_DESCRIPTION),
			fieldWithPath("y").type(JsonFieldType.NUMBER)
				.description(Constants.Y_DESCRIPTION),
			fieldWithPath("x").type(JsonFieldType.NUMBER)
				.description(Constants.X_DESCRIPTION)
		)
	);

	class Constants {

		private static final String ID_DESCRIPTION = "가게 아이디";
		private static final String STORENAME_DESCRIPTION = "가게 이름";
		private static final String CONTACTNUMBER_DESCRIPTION = "가게 연락처";
		private static final String ADDRESS_NAME_DESCRIPTION = "지번 주소";
		private static final String ROADADDRESS_NAME_DESCRIPTION = "도로명 주소";
		private static final String Y_DESCRIPTION = "위도";
		private static final String X_DESCRIPTION = "경도";
		private static final String RADIUS_DESCRIPTION = "위치 반경";
		private static final String KAKAO_PLACE_ID_DESCRIPTION = "카카오 로컬 API 장소 ID.";
		private static final String REQUEST_PAGE_TYPE_DESCRIPTION = "요청하는 페이지 타입 - feed | maps 로 나뉩니다.";
	}
}
