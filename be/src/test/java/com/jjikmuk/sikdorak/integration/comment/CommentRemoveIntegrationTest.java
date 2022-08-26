package com.jjikmuk.sikdorak.integration.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CommentRemove 통합테스트")
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
		void create_comment_success() {
			// given
			Review review = testData.user1PublicReview;
			User forky = testData.forky;
			Comment comment = testData.generator.comment(review, forky, "잘보고가요");

			// when
			commentService.removeComment(review.getId(),
				comment.getId(),
				createLoginUserWithUserId(forky.getId()));

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

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(review.getId(),
					comment.getId(),
					createLoginUserWithUserId(forky.getId())
				))
				.isInstanceOf(NotFoundReviewException.class);
		}

		@Test
		@DisplayName("존재하지 않는 댓글에 대해 삭제 요청이 주어진다면 예외를 발생시킨다")
		void remove_comment_with_not_existing_comment_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User forky = testData.forky;
			long notExistingCommentId = Long.MIN_VALUE;

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(review.getId(),
					notExistingCommentId,
					createLoginUserWithUserId(forky.getId())
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

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(review.getId(),
					comment.getId(),
					createLoginUserWithUserId(forky.getId())
				))
				.isInstanceOf(NotFoundCommentException.class);
		}

		@Test
		@DisplayName("로그인 하지 않은 유저가 댓글 삭제 요청을 한다면 예외를 발생시킨다")
		void remove_comment_with_not_existing_user_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User forky = testData.forky;
			Comment comment = testData.generator.comment(review, forky, "잘보고가요");

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(review.getId(),
					comment.getId(),
					new LoginUser(Authority.ANONYMOUS)
				))
				.isInstanceOf(NeedLoginException.class);
		}

		@Test
		@DisplayName("유저가 본인이 작성한 댓글이 아닌데 삭제 요청을 한다면 예외를 발생시킨다")
		void remove_comment_with_not_autor_will_failed() {
			// given
			Review review = testData.user1PublicReview;
			User forky = testData.forky;
			Comment comment = testData.generator.comment(review, forky, "잘보고가요");

			// then
			assertThatThrownBy(
				() -> commentService.removeComment(review.getId(),
					comment.getId(),
					createLoginUserWithUserId(testData.kukim.getId())
				))
				.isInstanceOf(UnauthorizedUserException.class);
		}
	}

	private static LoginUser createLoginUserWithUserId(long userId) {
		return new LoginUser(userId, Authority.USER);
	}
}
