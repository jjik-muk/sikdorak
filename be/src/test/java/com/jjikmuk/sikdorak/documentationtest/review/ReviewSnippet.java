package com.jjikmuk.sikdorak.documentationtest.review;

import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.requestPagingFieldsOfCommon;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfCommon;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfCommonNonData;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfObjectWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responsePagingFieldsOfCommon;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.jjikmuk.sikdorak.review.command.app.request.ReviewCreateRequest;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailLikeResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailStoreResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailUserResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

interface ReviewSnippet {

	Snippet REVIEW_CREATE_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		ReviewCreateRequest.class,
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

	Snippet REVIEW_CREATE_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

	Snippet REVIEW_MODIFY_REQUEST_SNIPPET = REVIEW_CREATE_REQUEST_SNIPPET;

	Snippet REVIEW_MODIFY_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

	Snippet REVIEW_MODIFY_REQUEST_PARAM_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디")
	);

	Snippet REVIEW_REMOVE_REQUEST_PARAM_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디")
	);

	Snippet REVIEW_REMOVE_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

	Snippet REVIEW_DETAIL_SEARCH_REQUEST_PARAM_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디")
	);

	Snippet REVIEW_DETAIL_SEARCH_RESPONSE_SUCESS_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailUserResponse.class,
			fieldWithPath("user").type(JsonFieldType.OBJECT).description("유저 정보"),
			fieldWithPath("user.userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
			fieldWithPath("user.userNickname").type(JsonFieldType.STRING).description("유저 이름"),
			fieldWithPath("user.userProfileImage").type(JsonFieldType.STRING)
				.description("유저 프로필 이미지")),

		responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailStoreResponse.class,
			fieldWithPath("store").type(JsonFieldType.OBJECT).description("가게 정보"),
			fieldWithPath("store.storeId").type(JsonFieldType.NUMBER).description("가게 아이디"),
			fieldWithPath("store.storeName").type(JsonFieldType.STRING).description("가게 이름"),
			fieldWithPath("store.addressName").type(JsonFieldType.STRING).description("가게 주소"),
			fieldWithPath("store.roadAddressName").type(JsonFieldType.STRING).description("가게 주소")),

		responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailLikeResponse.class,
			fieldWithPath("like.count").type(JsonFieldType.NUMBER).description("좋아요 개수"),
			fieldWithPath("like.userLikeStatus").type(JsonFieldType.BOOLEAN).description("유저의 좋아요 여부")),

		responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailResponse.class,
			fieldWithPath("reviewId").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
			fieldWithPath("reviewContent").type(JsonFieldType.STRING).description("리뷰 내용"),
			fieldWithPath("reviewScore").type(JsonFieldType.NUMBER).description("리뷰 점수"),
			fieldWithPath("reviewVisibility").type(JsonFieldType.STRING).description("리뷰 게시물의 공개 범위"),
			fieldWithPath("visitedDate").type(JsonFieldType.STRING).description("가게 방문일"),
			fieldWithPath("tags").type(JsonFieldType.ARRAY).description("리뷰를 표현하는 태그들"),
			fieldWithPath("images").type(JsonFieldType.ARRAY).description("리뷰를 위한 사진 URL"),
			fieldWithPath("createdAt").type(JsonFieldType.STRING).description("리뷰 생성 시간"),
			fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("리뷰 수정 시간")));

	Snippet REVIEW_DETAIL_SEARCH_RESPONSE_FAILED_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommonNonData());

	Snippet REVIEW_RECOMMEND_REQUEST_SNIPPET = requestPagingFieldsOfCommon();

	Snippet REVIEW_RECOMMEND_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailUserResponse.class,
			fieldWithPath("reviews[].user").type(JsonFieldType.OBJECT).description("유저 정보"),
			fieldWithPath("reviews[].user.userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
			fieldWithPath("reviews[].user.userNickname").type(JsonFieldType.STRING).description("유저 이름"),
			fieldWithPath("reviews[].user.userProfileImage").type(JsonFieldType.STRING)
				.description("유저 프로필 이미지")),

		responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailStoreResponse.class,
			fieldWithPath("reviews[].store").type(JsonFieldType.OBJECT).description("가게 정보"),
			fieldWithPath("reviews[].store.storeId").type(JsonFieldType.NUMBER).description("가게 아이디"),
			fieldWithPath("reviews[].store.storeName").type(JsonFieldType.STRING).description("가게 이름"),
			fieldWithPath("reviews[].store.addressName").type(JsonFieldType.STRING).description("지번 주소"),
			fieldWithPath("reviews[].store.roadAddressName").type(JsonFieldType.STRING).description("도로명 주소")),

		responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailLikeResponse.class,
			fieldWithPath("reviews[].like.count").type(JsonFieldType.NUMBER).description("좋아요 개수"),
			fieldWithPath("reviews[].like.userLikeStatus").type(JsonFieldType.BOOLEAN).description("유저의 좋아요 여부")),

		responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailResponse.class,
			fieldWithPath("reviews[].reviewId").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
			fieldWithPath("reviews[].reviewContent").type(JsonFieldType.STRING).description("리뷰 내용"),
			fieldWithPath("reviews[].reviewScore").type(JsonFieldType.NUMBER).description("리뷰 점수"),
			fieldWithPath("reviews[].reviewVisibility").type(JsonFieldType.STRING).description("리뷰 게시물의 공개 범위"),
			fieldWithPath("reviews[].visitedDate").type(JsonFieldType.STRING).description("가게 방문일"),
			fieldWithPath("reviews[].tags").type(JsonFieldType.ARRAY).description("리뷰를 표현하는 태그들"),
			fieldWithPath("reviews[].images").type(JsonFieldType.ARRAY).description("리뷰를 위한 사진 URL"),
			fieldWithPath("reviews[].createdAt").type(JsonFieldType.STRING).description("리뷰 생성 시간"),
			fieldWithPath("reviews[].updatedAt").type(JsonFieldType.STRING).description("리뷰 수정 시간")),

		responsePagingFieldsOfCommon()
	);

	Snippet REVIEW_LIKE_SUCCESS_REQUEST_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디")
	);

	Snippet REVIEW_LIKE_SUCCESS_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());
}
