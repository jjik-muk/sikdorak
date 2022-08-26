package com.jjikmuk.sikdorak.integration.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.comment.controller.request.CommentModifyRequest;
import com.jjikmuk.sikdorak.comment.domain.Comment;
import com.jjikmuk.sikdorak.comment.exception.NotFoundCommentException;
import com.jjikmuk.sikdorak.comment.repository.CommentRepository;
import com.jjikmuk.sikdorak.comment.service.CommentService;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.user.auth.controller.Authority;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.exception.NeedLoginException;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CommentModify 통합테스트")
class CommentModifyIntegrationTest extends InitIntegrationTest {

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Nested
	@DisplayName("댓글을 수정할 때")
	class ModifyCommentTest {

		@Test
		@DisplayName("정상적인 댓글 수정 요청이 주어진다면, 댓글이 수정된다.")
		void create_comment_success() {
			// given
			Review review = testData.user1PublicReview;
			User user1 = testData.forky;
			Comment comment = testData.generator.comment(review, user1, "잘보고가요");
			String updatedContent = "정말 맛있겠네요";

			// when
			commentService.modifyComment(review.getId(),
				comment.getId(),
				createLoginUserWithUserId(user1.getId()),
				new CommentModifyRequest(updatedContent)
			);

			// then
			Comment updatedComment = commentRepository.findById(comment.getId()).orElseThrow();
			assertThat(updatedComment.getId()).isEqualTo(comment.getId());
			assertThat(updatedComment.getUserId()).isEqualTo(comment.getUserId());
			assertThat(updatedComment.getReviewId()).isEqualTo(comment.getReviewId());
			assertThat(updatedComment.getCommentContent()).isEqualTo(updatedContent);
		}

		@Test
		@DisplayName("삭제 처리된 리뷰에 대해 댓글 수정 요청이 주어진다면 예외를 발생시킨다")
		void modify_comment_with_deleted_review_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User user1 = testData.forky;
			Comment comment = testData.generator.comment(review, user1, "잘보고가요");

			reviewRepository.delete(review);

			// then
			assertThatThrownBy(
				() -> commentService.modifyComment(review.getId(),
					comment.getId(),
					createLoginUserWithUserId(user1.getId()),
					new CommentModifyRequest("맛집이네요!")
				))
				.isInstanceOf(NotFoundReviewException.class);
		}

		@Test
		@DisplayName("존재하지 않는 댓글에 대해 수정 요청이 주어진다면 예외를 발생시킨다")
		void modify_comment_with_not_existing_comment_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User user1 = testData.forky;
			long notExistingCommentId = Long.MIN_VALUE;

			// then
			assertThatThrownBy(
				() -> commentService.modifyComment(review.getId(),
					notExistingCommentId,
					createLoginUserWithUserId(user1.getId()),
					new CommentModifyRequest("맛집이네요!")
				))
				.isInstanceOf(NotFoundCommentException.class);
		}

		@Test
		@DisplayName("삭제 처리된 댓글에 대해 수정 요청이 주어진다면 예외를 발생시킨다")
		void modify_comment_with_deleted_comment_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User user1 = testData.forky;
			Comment comment = testData.generator.comment(review, user1, "잘보고가요");
			commentRepository.delete(comment);

			// then
			assertThatThrownBy(
				() -> commentService.modifyComment(review.getId(),
					comment.getId(),
					createLoginUserWithUserId(user1.getId()),
					new CommentModifyRequest("맛집이네요!")
				))
				.isInstanceOf(NotFoundCommentException.class);
		}

		@Test
		@DisplayName("로그인 하지 않은 유저가 댓글 수정 요청을 한다면 예외를 발생시킨다")
		void modify_comment_with_not_existing_user_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User user1 = testData.forky;
			Comment comment = testData.generator.comment(review, user1, "잘보고가요");

			// then
			assertThatThrownBy(
				() -> commentService.modifyComment(review.getId(),
					comment.getId(),
					new LoginUser(Authority.ANONYMOUS),
					new CommentModifyRequest("맛집이네요!")
				))
				.isInstanceOf(NeedLoginException.class);
		}

		@Test
		@DisplayName("유저가 본인이 작성한 댓글이 아닌데 수정 요청을 한다면 예외를 발생시킨다")
		void modify_comment_with_not_autor_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User user1 = testData.forky;
			Comment comment = testData.generator.comment(review, user1, "잘보고가요");

			// then
			assertThatThrownBy(
				() -> commentService.modifyComment(review.getId(),
					comment.getId(),
					createLoginUserWithUserId(testData.kukim.getId()),
					new CommentModifyRequest("맛집이네요!")
				))
				.isInstanceOf(UnauthorizedUserException.class);
		}
	}

	private static LoginUser createLoginUserWithUserId(long userId) {
		return new LoginUser(userId, Authority.USER);
	}
}
