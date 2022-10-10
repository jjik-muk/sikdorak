package com.jjikmuk.sikdorak.documentationtest.comment;

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

import com.jjikmuk.sikdorak.comment.command.app.request.CommentCreateRequest;
import com.jjikmuk.sikdorak.comment.query.response.CommentSearchResponse;
import com.jjikmuk.sikdorak.user.user.query.response.UserSimpleProfileResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

interface CommentSnippet {

	// CREATE
	Snippet COMMENT_CREATE_PATH_VARIABLE_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디")
	);

	Snippet COMMENT_CREATE_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		CommentCreateRequest.class,
		fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
	);

	Snippet COMMENT_CREATE_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

	// MODIFY
	Snippet COMMENT_MODIFY_PATH_VARIABLE_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디"),
		parameterWithName("commentId").description("댓글 아이디")
	);

	Snippet COMMENT_MODIFY_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		CommentCreateRequest.class,
		fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
	);

	Snippet COMMENT_MODIFY_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

	// REMOVE
	Snippet COMMENT_REMOVE_PATH_VARIABLE_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디"),
		parameterWithName("commentId").description("댓글 아이디")
	);

	Snippet COMMENT_REMOVE_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

	// SEARCH
	Snippet COMMENT_SEARCH_PATH_VARIABLE_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디")
	);

	Snippet COMMENT_SEARCH_REQUEST_PARAMETER_SNIPPET = requestPagingFieldsOfCommon();

	Snippet COMMENT_SEARCH_RESPONSE_SNIPPET = createResponseSnippetWithFields(
		responseFieldsOfCommon(),

		responseFieldsOfObjectWithConstraintsAndFields(
			CommentSearchResponse.class,
			fieldWithPath("comments.[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
			fieldWithPath("comments.[].content").type(JsonFieldType.STRING).description("댓글 내용")
		),

		responseFieldsOfObjectWithConstraintsAndFields(
			UserSimpleProfileResponse.class,
			fieldWithPath("comments.[].author.id").type(JsonFieldType.NUMBER).description("작성자 아이디"),
			fieldWithPath("comments.[].author.nickname").type(JsonFieldType.STRING).description("작성자 닉네임"),
			fieldWithPath("comments.[].author.profileImage").type(JsonFieldType.STRING).description("작성자 프로필 이미지")
		),

		responsePagingFieldsOfCommon()
	);

}
