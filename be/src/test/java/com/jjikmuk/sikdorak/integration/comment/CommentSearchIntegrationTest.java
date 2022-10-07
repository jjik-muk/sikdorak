package com.jjikmuk.sikdorak.integration.comment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.comment.command.domain.Comment;
import com.jjikmuk.sikdorak.comment.query.CommentDao;
import com.jjikmuk.sikdorak.comment.query.response.CommentSearchPagingResponse;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import com.jjikmuk.sikdorak.common.exception.InvalidPageParameterException;
import com.jjikmuk.sikdorak.integration.InitIntegrationTest;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.review.command.domain.ReviewVisibility;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import com.jjikmuk.sikdorak.user.user.query.response.UserSimpleProfileResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("통합 : Comment 검색")
class CommentSearchIntegrationTest extends InitIntegrationTest {

    private static final CursorPageRequest FIRST_PAGE_REQUEST = new CursorPageRequest(0L, 0L, 10,
        true);

    @Autowired
    private CommentDao commentDao;

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
            Long reviewId = review.getId();
            LoginUser loginUser = testData.generator.createLoginUserWithUserId(
                forky.getId());

            // when
            CommentSearchPagingResponse response = commentDao.searchCommentsByReviewIdWithPaging(
                reviewId,
                loginUser,
                FIRST_PAGE_REQUEST
            );

            // then
            assertThat(response.comments()).satisfies(
                commentResponseList -> commentResponseList.forEach(
                    commentResponse -> {
                        // 댓글 검증
                        assertThat(commentResponse.id()).isEqualTo(comment.getId());
                        assertThat(commentResponse.content()).isEqualTo(
                            comment.getCommentContent());

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
            Long reviewId = review.getId();
            LoginUser loginUser = testData.generator.createLoginUserWithUserId(
                testData.kukim.getId());

            reviewRepository.delete(review);

            // then
            assertThatThrownBy(() -> commentDao.searchCommentsByReviewIdWithPaging(reviewId,
                loginUser,
                FIRST_PAGE_REQUEST))
                .isInstanceOf(NotFoundReviewException.class);
        }

        @Test
        @DisplayName("유저가 볼 수 없는 리뷰의 댓글 조회 요청을 한다면 예외를 발생시킨다")
        void search_comment_with_not_autor_will_failed() {
            // given
            User forky = testData.forky;
            User jay = testData.jay;
            Review review = testData.generator.review(jay, ReviewVisibility.PROTECTED);
            Long reviewId = review.getId();
            LoginUser loginUser = testData.generator.createLoginUserWithUserId(forky.getId());

            forky.unfollow(jay);

            // then
            assertThatThrownBy(
                () -> commentDao.searchCommentsByReviewIdWithPaging(
                    reviewId,
                    loginUser,
                    FIRST_PAGE_REQUEST))
                .isInstanceOf(UnauthorizedUserException.class);
        }

        @Test
        @DisplayName("페이지 크기 최대값을 넘으면 예외가 발생한다.")
        void search_comment_with_size_max_over_failed() {
            // given
            User forky = testData.forky;
            Review review = testData.generator.review(forky, ReviewVisibility.PROTECTED);
            Long reviewId = review.getId();
            LoginUser loginUser = testData.generator.createLoginUserWithUserId(forky.getId());
            CursorPageRequest cursorPageRequest = new CursorPageRequest(0L, 0L, Integer.MAX_VALUE, true);

            // then
            assertThatThrownBy(
                () -> commentDao.searchCommentsByReviewIdWithPaging(
                    reviewId,
                    loginUser,
                    cursorPageRequest))
                .isInstanceOf(InvalidPageParameterException.class);
        }

        @Test
        @DisplayName("이전 이후 페이지가 정상적으로 조회된다.")
        void search_comment_with_paging_success() {
            User jay = testData.jay;
            Review review = testData.generator.review(testData.jay, ReviewVisibility.PRIVATE);
            for (int i = 0; i < 15; i++) {
                testData.generator.comment(review, jay, "내용 " + i);
            }

            CommentSearchPagingResponse response = commentDao.searchCommentsByReviewIdWithPaging(
                review.getId(),
                testData.generator.createLoginUserWithUserId(jay.getId()),
                new CursorPageRequest(0L, 0L, 5, true));

            CursorPageResponse page = response.page();

            CommentSearchPagingResponse response2 = commentDao.searchCommentsByReviewIdWithPaging(
                review.getId(),
                testData.generator.createLoginUserWithUserId(jay.getId()),
                new CursorPageRequest(0L, page.next(), 5, true));

            page = response2.page();

            CommentSearchPagingResponse response3 = commentDao.searchCommentsByReviewIdWithPaging(
                review.getId(),
                testData.generator.createLoginUserWithUserId(jay.getId()),
                new CursorPageRequest(0L, page.next(), 5, true));

            page = response3.page();

            CommentSearchPagingResponse response4 = commentDao.searchCommentsByReviewIdWithPaging(
                review.getId(),
                testData.generator.createLoginUserWithUserId(jay.getId()),
                new CursorPageRequest(page.prev(), 0L, 5, false));

            page = response4.page();

            CommentSearchPagingResponse response5 = commentDao.searchCommentsByReviewIdWithPaging(
                review.getId(),
                testData.generator.createLoginUserWithUserId(jay.getId()),
                new CursorPageRequest(page.prev(), page.next(), 5, false));

            assertThat(response.comments()).size().isEqualTo(5);
            assertThat(response2.comments()).size().isEqualTo(5);
            assertThat(response3.comments()).size().isEqualTo(5);
            assertThat(response4.comments()).size().isEqualTo(5);
            assertThat(response5.comments()).size().isEqualTo(5);
        }
    }
}
