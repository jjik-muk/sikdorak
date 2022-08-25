package com.jjikmuk.sikdorak.acceptance.comment;

import static com.jjikmuk.sikdorak.acceptance.comment.CommentSnippet.COMMENT_MODIFY_PATH_VARIABLE_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.comment.CommentSnippet.COMMENT_MODIFY_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.acceptance.comment.CommentSnippet.COMMENT_MODIFY_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.comment.controller.request.CommentModifyRequest;
import com.jjikmuk.sikdorak.comment.domain.Comment;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.user.user.domain.User;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("CommentModify 인수테스트")
class CommentModifyAcceptanceTest extends InitAcceptanceTest {


	@Test
	@DisplayName("댓글 수정 요청이 정상적인 경우라면 댓글 생성 후 정상 상태 코드를 반환한다")
	void modify_comment_success() {
		Review review = testData.user1PublicReview;
		User user1 = testData.user1;
		Comment savedComment = testData.saveAndGetComment(review, user1, "안녕하세요");

		CommentModifyRequest commentModifyRequest = new CommentModifyRequest(
			"좋은 리뷰 감사합니다"
		);

		ResponseCodeAndMessages expectedCodeAndMessage = ResponseCodeAndMessages.COMMENT_MODIFY_SUCCESS;

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH,
				COMMENT_MODIFY_PATH_VARIABLE_SNIPPET,
				COMMENT_MODIFY_REQUEST_SNIPPET,
				COMMENT_MODIFY_RESPONSE_SNIPPET))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.header("Authorization", testData.user1ValidAuthorizationHeader)
			.body(commentModifyRequest)

		.when()
			.put("/api/reviews/{reviewId}/comments/{commentId}", review.getId(), savedComment.getReviewId())

		.then().log().all()
			.statusCode(HttpStatus.OK.value())
			.body("code", Matchers.equalTo(expectedCodeAndMessage.getCode()))
			.body("message", Matchers.equalTo(expectedCodeAndMessage.getMessage()));
	}
}
