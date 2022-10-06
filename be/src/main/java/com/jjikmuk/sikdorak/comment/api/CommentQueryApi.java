package com.jjikmuk.sikdorak.comment.api;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.COMMENT_SEARCH_SUCCESS;

import com.jjikmuk.sikdorak.comment.query.CommentDao;
import com.jjikmuk.sikdorak.comment.query.response.CommentSearchPagingResponse;
import com.jjikmuk.sikdorak.common.controller.CursorPageable;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews/{reviewId}/comments")
@RequiredArgsConstructor
public class CommentQueryApi {

	private final CommentDao commentDao;

	@GetMapping
	public CommonResponseEntity<CommentSearchPagingResponse> searchComment(
		@PathVariable long reviewId,
		@AuthenticatedUser LoginUser loginUser,
		@CursorPageable CursorPageRequest pagingRequest
	) {
		return new CommonResponseEntity<>(
			COMMENT_SEARCH_SUCCESS,
			commentDao.searchCommentsByReviewIdWithPaging(reviewId, loginUser, pagingRequest),
			HttpStatus.OK);
	}
}
