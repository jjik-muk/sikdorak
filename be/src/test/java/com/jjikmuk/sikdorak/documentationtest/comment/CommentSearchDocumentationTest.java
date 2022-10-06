package com.jjikmuk.sikdorak.documentationtest.comment;

import static com.jjikmuk.sikdorak.documentationtest.comment.CommentSnippet.COMMENT_SEARCH_PATH_VARIABLE_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.comment.CommentSnippet.COMMENT_SEARCH_REQUEST_PARAMETER_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.comment.CommentSnippet.COMMENT_SEARCH_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : Comment 검색")
class CommentSearchDocumentationTest extends InitDocumentationTest {

	@Test
	@DisplayName("댓글 조회 요청이 정상적인 경우라면 댓글 목록과 정상 상태 코드를 반환한다")
	void search_comment_success() {
		Review review = testData.user1PublicReview;
		User kukim = testData.kukim;

		testData.generator.comment(review, kukim, "맛집이군요");

		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.COMMENT_SEARCH_SUCCESS;

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				COMMENT_SEARCH_PATH_VARIABLE_SNIPPET,
				COMMENT_SEARCH_REQUEST_PARAMETER_SNIPPET,
				COMMENT_SEARCH_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.generator.validAuthorizationHeader(kukim))
			.queryParam("size", "10")
			.queryParam("after", "0")

		.when()
			.get("/api/reviews/{reviewId}/comments", review.getId())

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(expectedCodeAndMessage.getCode()))
			.body("message", Matchers.equalTo(expectedCodeAndMessage.getMessage()));
	}
}
