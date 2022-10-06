package com.jjikmuk.sikdorak.documentationtest.comment;

import static com.jjikmuk.sikdorak.documentationtest.comment.CommentSnippet.COMMENT_REMOVE_PATH_VARIABLE_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.comment.CommentSnippet.COMMENT_REMOVE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.comment.command.domain.Comment;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : Comment 삭제")
class CommentRemoveDocumentationTest extends InitDocumentationTest {


	@Test
	@DisplayName("댓글 삭제 요청이 정상적인 경우라면 댓글 삭제 후 정상 상태 코드를 반환한다")
	void remove_comment_success() {
		Review review = testData.user1PublicReview;
		User kukim = testData.kukim;
		Comment savedComment = testData.generator.comment(review, kukim, "안녕하세요");

		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.COMMENT_REMOVE_SUCCESS;

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				COMMENT_REMOVE_PATH_VARIABLE_SNIPPET,
				COMMENT_REMOVE_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.generator.validAuthorizationHeader(kukim))

		.when()
			.delete("/api/reviews/{reviewId}/comments/{commentId}", review.getId(), savedComment.getReviewId())

		.then()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(expectedCodeAndMessage.getCode()))
			.body("message", Matchers.equalTo(expectedCodeAndMessage.getMessage()));
	}
}
