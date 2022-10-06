package com.jjikmuk.sikdorak.integration.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.comment.command.app.CommentService;
import com.jjikmuk.sikdorak.comment.command.app.request.CommentCreateRequest;
import com.jjikmuk.sikdorak.comment.command.domain.Comment;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.user.domain.Authority;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Comment 생성")
class CommentCreateIntegrationTest extends InitIntegrationTest {

	@Autowired
	private CommentService commentService;

	@Nested
	@DisplayName("댓글을 작성할 때")
	class CreateCommentTest {

		@Test
		@DisplayName("정상적인 댓글 작성 요청이 주어진다면, 댓글이 생성된다.")
		void create_comment_success() {
			// given
			long reviewId = testData.user1PublicReview.getId();
			LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
			CommentCreateRequest createRequest = new CommentCreateRequest(
				"맛집이네요!"
			);

			// when
			Comment savedComment = commentService.createComment(reviewId, loginUser, createRequest);

			// then
			assertThat(reviewId).isEqualTo(savedComment.getReviewId());
			assertThat(loginUser.getId()).isEqualTo(savedComment.getUserId());
			assertThat(createRequest.getContent()).isEqualTo(savedComment.getCommentContent());
		}

		@Test
		@DisplayName("존재하지 않는 게시글에 대해 리뷰 생성 요청이 주어진다면 예외를 발생시킨다")
		void create_comment_with_not_existing_review_will_failed() {
			// given
			long notExistsReviewId = Long.MIN_VALUE;
			LoginUser loginUser = new LoginUser(testData.kukim.getId(), Authority.USER);
			CommentCreateRequest createRequest = new CommentCreateRequest(
				"맛집이네요!"
			);

			// then
			assertThatThrownBy(
				() -> commentService.createComment(notExistsReviewId, loginUser, createRequest))
				.isInstanceOf(NotFoundReviewException.class);
		}

		@Test
		@DisplayName("존재하지 않는 유저가 댓글 생성 요청을 한다면 예외를 발생시킨다")
		void create_comment_with_not_existing_user_will_failed() {
			// given
			long reviewId = testData.user1PublicReview.getId();
			long notExistingUserId = Long.MIN_VALUE;
			LoginUser loginUser = new LoginUser(notExistingUserId, Authority.USER);
			CommentCreateRequest createRequest = new CommentCreateRequest(
				"맛집이네요!"
			);

			// then
			assertThatThrownBy(
				() -> commentService.createComment(reviewId, loginUser, createRequest))
				.isInstanceOf(NotFoundUserException.class);
		}
	}
}
