package com.jjikmuk.sikdorak.comment.query;

import com.jjikmuk.sikdorak.comment.command.domain.Comment;
import com.jjikmuk.sikdorak.comment.command.domain.CommentRepository;
import com.jjikmuk.sikdorak.comment.query.response.CommentSearchPagingResponse;
import com.jjikmuk.sikdorak.comment.query.response.CommentSearchResponse;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.controller.response.CursorPageResponse;
import com.jjikmuk.sikdorak.common.exception.InvalidPageParameterException;
import com.jjikmuk.sikdorak.review.command.domain.Review;
import com.jjikmuk.sikdorak.review.command.domain.ReviewRepository;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.user.command.domain.RelationType;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import com.jjikmuk.sikdorak.user.user.command.domain.UserRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentDao {

    private static final long FIRST_CURSOR_ID = 0L;
    private static final long LAST_CURSOR_ID = 0L;
    private static final int COMMENT_PAGE_MAX_SIZE = 50;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public CommentSearchPagingResponse searchCommentsByReviewIdWithPaging(long reviewId,
        LoginUser loginUser,
        CursorPageRequest pageRequest) {
        // 검증
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(NotFoundReviewException::new);
        validateReviewReadable(review, loginUser);
        validatePageSize(pageRequest);

        // 페이지 가져오기
        List<Comment> comments = getCommentsByReviewIdWithPaging(reviewId, pageRequest);

        // 사용자 정보 가져오기
        Map<Long, User> usersMap = getUsersMap(getCommentUserIds(comments));

        // 댓글 검색 결과 리스트 가져오기
        List<CommentSearchResponse> commentResponses = getCommentSearchResponses(comments, usersMap);

        if (commentResponses.isEmpty()) {
            return new CommentSearchPagingResponse(
                commentResponses,
                getCursorResponse(commentResponses, FIRST_CURSOR_ID, LAST_CURSOR_ID, pageRequest)
            );
        }

        // 응답 객체 반환
        return new CommentSearchPagingResponse(
            getCommentSearchResponses(comments, usersMap),
            getCursorResponse(
                commentResponses,
                getFirstCommentId(comments),
                getLastCommentId(comments),
                pageRequest)
        );
    }

    private void validateReviewReadable(Review review, LoginUser loginUser) {
        User reviewAuthor = userRepository.findById(review.getUserId())
            .orElseThrow(NotFoundUserException::new);

        RelationType relationType = reviewAuthor.relationTypeTo(loginUser);

        if (!review.isReadable(relationType)) {
            throw new UnauthorizedUserException();
        }
    }

    private void validatePageSize(CursorPageRequest pageRequest) {
        if (pageRequest.getSize() > COMMENT_PAGE_MAX_SIZE) {
            throw new InvalidPageParameterException();
        }
    }

    private List<Comment> getCommentsByReviewIdWithPaging(long reviewId,
        CursorPageRequest pagingRequest) {
        Pageable size = Pageable.ofSize(pagingRequest.getSize());
        if (pagingRequest.isFirstPage()) {
            return commentRepository.findCommentsByReviewIdWithFirstPage(reviewId, size);
        }

        if (pagingRequest.isAfter()) {
            return commentRepository.findCommentsByReviewIdWithPagingAfter(reviewId,
                pagingRequest.getAfter(), size);
        }

        return commentRepository.findCommentsByReviewIdWithPagingBefore(reviewId,
                pagingRequest.getBefore(), size)
            .stream()
            .sorted((c1, c2) -> c2.getCreatedAt().compareTo(c1.getCreatedAt()))
            .toList();
    }

    private Map<Long, User> getUsersMap(List<Long> commentUserIds) {
        return userRepository.findAllById(commentUserIds)
            .stream()
            .collect(Collectors.toMap(User::getId, Function.identity()));
    }

    private List<Long> getCommentUserIds(List<Comment> comments) {
        return comments.stream()
            .map(Comment::getUserId)
            .toList();
    }

    private List<CommentSearchResponse> getCommentSearchResponses(List<Comment> comments,
        Map<Long, User> usersMap) {
        return comments.stream()
            .map(comment -> CommentSearchResponse.of(comment, usersMap.get(comment.getUserId())))
            .toList();
    }

    private long getFirstCommentId(List<Comment> comments) {
        return comments.stream()
            .findFirst()
            .map(Comment::getId)
            .orElse(0L);
    }

    private long getLastCommentId(List<Comment> comments) {
        return comments.stream()
            .reduce((first, second) -> second)
            .map(Comment::getId)
            .orElse(0L);
    }

    private CursorPageResponse getCursorResponse(List<?> list, long firstItemId, long lastItemId,
        CursorPageRequest cursorPageRequest) {
        if (list.isEmpty()) {
            return new CursorPageResponse(0, FIRST_CURSOR_ID, LAST_CURSOR_ID, true);
        }

        long count = commentRepository.countAllByIdBefore(lastItemId);
        boolean isLast = count == 0;

        return new CursorPageResponse(cursorPageRequest.getSize(), firstItemId, lastItemId,
            isLast);
    }
}
