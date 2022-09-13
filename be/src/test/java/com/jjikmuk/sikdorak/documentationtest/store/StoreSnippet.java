package com.jjikmuk.sikdorak.documentationtest.store;

import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.DEFAULT_PAGING_AFTER_REQUEST_PARAMETER;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.DEFAULT_PAGING_BEFORE_REQUEST_PARAMETER;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.DEFAULT_PAGING_SIZE_REQUEST_PARAMETER;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.requestPagingFieldsOfCommon;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfCommon;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfCommonNonData;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfListWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfObjectWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responsePagingFieldsOfCommon;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailLikeResponse;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailStoreResponse;
import com.jjikmuk.sikdorak.review.controller.response.reviewdetail.ReviewDetailUserResponse;
import com.jjikmuk.sikdorak.store.controller.request.StoreCreateRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreModifyRequest;
import com.jjikmuk.sikdorak.store.controller.request.StoreVerifyOrSaveRequest;
import com.jjikmuk.sikdorak.store.controller.response.StoreDetailResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreRadiusSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreSearchResponse;
import com.jjikmuk.sikdorak.store.controller.response.StoreVerifyOrSaveResponse;
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
				.description(Constants.STORE_NAME_DESCRIPTION),
			fieldWithPath("contactNumber").type(JsonFieldType.STRING)
				.description(Constants.CONTACT_NUMBER_DESCRIPTION),
			fieldWithPath("addressName").type(JsonFieldType.STRING)
				.description(Constants.ADDRESS_NAME_DESCRIPTION),
			fieldWithPath("roadAddressName").type(JsonFieldType.STRING)
				.description(Constants.ROAD_ADDRESS_NAME_DESCRIPTION).optional(),
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
			.description(Constants.STORE_NAME_DESCRIPTION),
		fieldWithPath("contactNumber").type(JsonFieldType.STRING)
			.description(Constants.CONTACT_NUMBER_DESCRIPTION),
		fieldWithPath("addressName").type(JsonFieldType.STRING)
			.description(Constants.ADDRESS_NAME_DESCRIPTION),
		fieldWithPath("roadAddressName").type(JsonFieldType.STRING)
			.description(Constants.ROAD_ADDRESS_NAME_DESCRIPTION).optional(),
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
			.description(Constants.STORE_NAME_DESCRIPTION),
		fieldWithPath("contactNumber").type(JsonFieldType.STRING)
			.description(Constants.CONTACT_NUMBER_DESCRIPTION),
		fieldWithPath("addressName").type(JsonFieldType.STRING)
			.description(Constants.ADDRESS_NAME_DESCRIPTION),
		fieldWithPath("roadAddressName").type(JsonFieldType.STRING)
			.description(Constants.ROAD_ADDRESS_NAME_DESCRIPTION).optional(),
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

	Snippet STORE_REMOVE_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommonNonData());

	// 근처 가게 조회
	Snippet STORE_SEARCH_BY_RADIUS_REQUEST_SNIPPET = requestParameters(
		parameterWithName("type").description(Constants.REQUEST_PAGE_TYPE_DESCRIPTION),
		parameterWithName("x").description(Constants.X_DESCRIPTION),
		parameterWithName("y").description(Constants.Y_DESCRIPTION),
		parameterWithName("radius").description(Constants.RADIUS_DESCRIPTION),
		DEFAULT_PAGING_SIZE_REQUEST_PARAMETER,
		DEFAULT_PAGING_BEFORE_REQUEST_PARAMETER,
		DEFAULT_PAGING_AFTER_REQUEST_PARAMETER
	);

	Snippet STORE_SEARCH_BY_RADIUS_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfObjectWithConstraintsAndFields(
			StoreRadiusSearchResponse.class,
			fieldWithPath("stores[].id").type(JsonFieldType.NUMBER).description(Constants.ID_DESCRIPTION),
			fieldWithPath("stores[].storeName").type(JsonFieldType.STRING)
				.description(Constants.STORE_NAME_DESCRIPTION),
			fieldWithPath("stores[].contactNumber").type(JsonFieldType.STRING)
				.description(Constants.CONTACT_NUMBER_DESCRIPTION),
			fieldWithPath("stores[].addressName").type(JsonFieldType.STRING)
				.description(Constants.ADDRESS_NAME_DESCRIPTION),
			fieldWithPath("stores[].roadAddressName").type(JsonFieldType.STRING)
				.description(Constants.ROAD_ADDRESS_NAME_DESCRIPTION).optional(),
			fieldWithPath("stores[].x").type(JsonFieldType.NUMBER)
				.description(Constants.X_DESCRIPTION),
			fieldWithPath("stores[].y").type(JsonFieldType.NUMBER)
				.description(Constants.Y_DESCRIPTION)
		),

		responsePagingFieldsOfCommon()
	);

	// 가게 등록 검증
	Snippet STORE_VERIFY_OR_SAVE_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		StoreVerifyOrSaveRequest.class,
		fieldWithPath("placeId").type(JsonFieldType.NUMBER)
			.description(Constants.CONTACT_NUMBER_DESCRIPTION),
		fieldWithPath("storeName").type(JsonFieldType.STRING)
			.description(Constants.STORE_NAME_DESCRIPTION),
		fieldWithPath("y").type(JsonFieldType.NUMBER)
			.description(Constants.Y_DESCRIPTION),
		fieldWithPath("x").type(JsonFieldType.NUMBER)
			.description(Constants.X_DESCRIPTION)
	);

	Snippet STORE_VERIFY_OR_SAVE_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfObjectWithConstraintsAndFields(
			StoreVerifyOrSaveResponse.class,
			fieldWithPath("storeId").type(JsonFieldType.NUMBER)
				.description(Constants.ID_DESCRIPTION),
			fieldWithPath("placeId").type(JsonFieldType.NUMBER)
				.description(Constants.KAKAO_PLACE_ID_DESCRIPTION),
			fieldWithPath("storeName").type(JsonFieldType.STRING)
				.description(Constants.STORE_NAME_DESCRIPTION),
			fieldWithPath("y").type(JsonFieldType.NUMBER)
				.description(Constants.Y_DESCRIPTION),
			fieldWithPath("x").type(JsonFieldType.NUMBER)
				.description(Constants.X_DESCRIPTION)
		)
	);

	// 가게 상세 조회
	Snippet STORE_SEARCH_DETAIL_REQUEST_PARAM_SNIPPET = pathParameters(
		parameterWithName("storeId").description(Constants.ID_DESCRIPTION)
	);

	Snippet STORE_SEARCH_DETAIL_RESPONSE_SNIPPET = DocumentFormatGenerator.createResponseSnippetWithFields(
		DocumentFormatGenerator.responseFieldsOfCommon(),

		responseFieldsOfObjectWithConstraintsAndFields(
			StoreDetailResponse.class,
			fieldWithPath("storeId").type(JsonFieldType.NUMBER)
				.description(Constants.ID_DESCRIPTION),
			fieldWithPath("storeName").type(JsonFieldType.STRING)
				.description(Constants.STORE_NAME_DESCRIPTION),
			fieldWithPath("addressName").type(JsonFieldType.STRING).description(
				Constants.ADDRESS_NAME_DESCRIPTION),
			fieldWithPath("roadAddressName").type(JsonFieldType.STRING).description(
				Constants.ROAD_ADDRESS_NAME_DESCRIPTION).optional(),
			fieldWithPath("contactNumber").type(JsonFieldType.STRING).description(
				Constants.CONTACT_NUMBER_DESCRIPTION),
			fieldWithPath("y").type(JsonFieldType.NUMBER).description(Constants.Y_DESCRIPTION),
			fieldWithPath("x").type(JsonFieldType.NUMBER).description(Constants.X_DESCRIPTION),
			fieldWithPath("reviewCounts").type(JsonFieldType.NUMBER)
				.description(Constants.REVIEW_COUNTS),
			fieldWithPath("reviewScoreAverage").type(JsonFieldType.NUMBER)
				.description(Constants.REVIEW_SCORE_AVERAGE)
		)
	);

	class Constants {

		private static final String ID_DESCRIPTION = "가게 아이디";
		private static final String STORE_NAME_DESCRIPTION = "가게 이름";
		private static final String CONTACT_NUMBER_DESCRIPTION = "가게 연락처";
		private static final String ADDRESS_NAME_DESCRIPTION = "지번 주소";
		private static final String ROAD_ADDRESS_NAME_DESCRIPTION = "도로명 주소";
		private static final String Y_DESCRIPTION = "위도";
		private static final String X_DESCRIPTION = "경도";
		private static final String RADIUS_DESCRIPTION = "위치 반경";
		private static final String KAKAO_PLACE_ID_DESCRIPTION = "API 에서 제공된 장소 ID";
		private static final String REQUEST_PAGE_TYPE_DESCRIPTION = "요청하는 페이지 타입 - feed | maps 로 나뉩니다.";
		private static final String REVIEW_SCORE_AVERAGE = "가게의 리뷰 평균 점수 입니다.";
		private static final String REVIEW_COUNTS = "가게의 리뷰 개수입니다.";
	}
	
}
