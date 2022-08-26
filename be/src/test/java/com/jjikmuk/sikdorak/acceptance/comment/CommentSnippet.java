package com.jjikmuk.sikdorak.acceptance.comment;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfCommonNonData;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.jjikmuk.sikdorak.comment.controller.request.CommentCreateRequest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

interface CommentSnippet {

	Snippet COMMENT_CREATE_PATH_VARIABLE_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디")
	);

	Snippet COMMENT_CREATE_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		CommentCreateRequest.class,
		fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
	);

	Snippet COMMENT_CREATE_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

	Snippet COMMENT_MODIFY_PATH_VARIABLE_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디"),
		parameterWithName("commentId").description("댓글 아이디")
	);

	Snippet COMMENT_MODIFY_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
		CommentCreateRequest.class,
		fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
	);

	Snippet COMMENT_MODIFY_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

	Snippet COMMENT_REMOVE_PATH_VARIABLE_SNIPPET = pathParameters(
		parameterWithName("reviewId").description("리뷰 아이디"),
		parameterWithName("commentId").description("댓글 아이디")
	);

	Snippet COMMENT_REMOVE_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

}
