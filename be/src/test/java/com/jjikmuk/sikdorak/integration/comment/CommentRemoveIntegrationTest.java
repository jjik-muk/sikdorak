package com.jjikmuk.sikdorak.integration.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.comment.command.app.CommentService;
import com.jjikmuk.sikdorak.comment.command.domain.Comment;
import com.jjikmuk.sikdorak.comment.command.domain.CommentRepository;
import com.jjikmuk.sikdorak.comment.exception.NotFoundCommentException;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Comment 수정")
class CommentRemoveIntegrationTest extends InitIntegrationTest {

	@Autowired
	private CommentService commentService;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Nested
	@DisplayName("댓글을 삭제할 때")
	class RemoveCommentTest {

		@Test
		@DisplayName("정상적인 댓글 삭제 요청이 주어진다면, 댓글이 삭제된다.")
		void remove_comment_success() {
			// given
			Review review = testData.user1PublicReview;
			User forky = testData.forky;
			Comment comment = testData.generator.comment(review, forky, "잘보고가요");
			LoginUser forkyLoginUser = testData.generator.createLoginUserWithUserId(forky.getId());

			// when
			commentService.removeComment(review.getId(),
				comment.getId(),
				forkyLoginUser
				);

			// then
			Optional<Comment> findResult = commentRepository.findById(comment.getId());
			assertThat(findResult).isEmpty();
		}

		@Test
		@DisplayName("삭제 처리된 리뷰에 대해 댓글 삭제 요청이 주어진다면 예외를 발생시킨다")
		void remove_comment_with_deleted_review_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User forky = testData.forky;
			Comment comment = testData.generator.comment(review, forky, "잘보고가요");

			reviewRepository.delete(review);

			long reviewId = review.getId();
			long commentId = comment.getId();
			LoginUser forkyLoginUser = testData.generator.createLoginUserWithUserId(forky.getId());

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(reviewId,
					commentId,
					forkyLoginUser
				))
				.isInstanceOf(NotFoundReviewException.class);
		}

		@Test
		@DisplayName("존재하지 않는 댓글에 대해 삭제 요청이 주어진다면 예외를 발생시킨다")
		void remove_comment_with_not_existing_comment_will_failed() {
			// given
			long reviewId = testData.user1PublicReview.getId();
			long notExistingCommentId = Long.MIN_VALUE;
			long forkyId = testData.forky.getId();
			LoginUser forkyLoginUser = testData.generator.createLoginUserWithUserId(forkyId);

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(reviewId,
					notExistingCommentId,
					forkyLoginUser
				))
				.isInstanceOf(NotFoundCommentException.class);
		}

		@Test
		@DisplayName("삭제 처리된 댓글에 대해 삭제 요청이 주어진다면 예외를 발생시킨다")
		void remove_comment_with_deleted_comment_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User forky = testData.forky;
			Comment comment = testData.generator.comment(review, forky, "잘보고가요");
			commentRepository.delete(comment);

			long reviewId = review.getId();
			long commentId = comment.getId();
			LoginUser forkyLoginUser = testData.generator.createLoginUserWithUserId(forky.getId());

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(reviewId,
					commentId,
					forkyLoginUser
				))
				.isInstanceOf(NotFoundCommentException.class);
		}

		@Test
		@DisplayName("유저가 본인이 작성한 댓글이 아닌데 삭제 요청을 한다면 예외를 발생시킨다")
		void remove_comment_with_not_autor_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User forky = testData.forky;
			Comment comment = testData.generator.comment(review, forky, "잘보고가요");

			long reviewId = review.getId();
			long commentId = comment.getId();
			LoginUser kukimLoginUser = testData.generator.createLoginUserWithUserId(testData.kukim.getId());

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(reviewId,
					commentId,
					kukimLoginUser
				))
				.isInstanceOf(UnauthorizedUserException.class);
		}
	}
}
