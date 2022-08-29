package com.jjikmuk.sikdorak.integration.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.comment.controller.response.CommentSearchPagingResponse;
import com.jjikmuk.sikdorak.comment.domain.Comment;
import com.jjikmuk.sikdorak.comment.service.CommentService;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.domain.Review;
import com.jjikmuk.sikdorak.review.domain.ReviewVisibility;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.review.repository.ReviewRepository;
import com.jjikmuk.sikdorak.user.user.controller.response.UserSimpleProfileResponse;
import com.jjikmuk.sikdorak.user.user.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("CommentSearch 통합테스트")
class CommentSearchIntegrationTest extends InitIntegrationTest {

	private static final CursorPageRequest FIRST_PAGE_REQUEST = new CursorPageRequest(0L, 0L, 10,
		true);

	@Autowired
	private CommentService commentService;

	@Autowired
	private ReviewRepository reviewRepository;

	@Nested
	@DisplayName("댓글을 조회할 때")
	class SearchCommentTest {

		@Test
		@DisplayName("정상적인 댓글 조회 요청이 주어진다면, 댓글목록이 반환된다.")
		void search_comment_success() {
			// given
			User forky = testData.forky;
			Review review = testData.generator.review(forky, ReviewVisibility.PUBLIC);
			Comment comment = testData.generator.comment(review, forky, "잘보고가요");

			// when
			CommentSearchPagingResponse response = commentService.searchCommentsByReviewIdWithPaging(
				review.getId(),
				testData.generator.createLoginUserWithUserId(forky.getId()),
				FIRST_PAGE_REQUEST
			);

			// then
			assertThat(response.comments()).satisfies(
				commentResponseList -> commentResponseList.forEach(
					commentResponse -> {
						// 댓글 검증
						assertThat(commentResponse.id()).isEqualTo(comment.getId());
						assertThat(commentResponse.content()).isEqualTo(comment.getCommentContent());

						// 작성자 검증
						UserSimpleProfileResponse author = commentResponse.author();
						assertThat(author).isNotNull();
						assertThat(author.id()).isEqualTo(forky.getId());
						assertThat(author.nickname()).isEqualTo(forky.getNickname());
						assertThat(author.profileImage()).isEqualTo(forky.getProfileImage());
					}));
		}

		@Test
		@DisplayName("삭제 처리된 리뷰에 대해 댓글 조회 요청이 주어진다면 예외를 발생시킨다")
		void search_comment_with_deleted_review_will_failed() {
			// given
			Review review = testData.user1PublicReview;

			reviewRepository.delete(review);

			// then
			assertThatThrownBy(
				() -> commentService.searchCommentsByReviewIdWithPaging(review.getId(),
					testData.generator.createLoginUserWithUserId(testData.kukim.getId()),
					FIRST_PAGE_REQUEST))
				.isInstanceOf(NotFoundReviewException.class);
		}

		@Test
		@DisplayName("유저가 볼 수 없는 리뷰의 댓글 조회 요청을 한다면 예외를 발생시킨다")
		void search_comment_with_not_autor_will_failed() {
			// given
			User forky = testData.forky;
			User jay = testData.jay;

			forky.unfollow(jay);

			Review review = testData.generator.review(jay, ReviewVisibility.PROTECTED);

			// then
			assertThatThrownBy(
				() -> commentService.searchCommentsByReviewIdWithPaging(
					review.getId(),
					testData.generator.createLoginUserWithUserId(forky.getId()),
					FIRST_PAGE_REQUEST))
				.isInstanceOf(UnauthorizedUserException.class);
		}
	}
}
