package com.jjikmuk.sikdorak.comment.controller;

import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.COMMENT_CREATE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.COMMENT_MODIFY_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.COMMENT_REMOVE_SUCCESS;
import static com.jjikmuk.sikdorak.common.ResponseCodeAndMessages.COMMENT_SEARCH_SUCCESS;

import com.jjikmuk.sikdorak.comment.service.CommentService;
import com.jjikmuk.sikdorak.comment.service.request.CommentCreateRequest;
import com.jjikmuk.sikdorak.comment.service.request.CommentModifyRequest;
import com.jjikmuk.sikdorak.comment.service.response.CommentSearchPagingResponse;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.controller.CursorPageable;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews/{reviewId}/comments")
@RequiredArgsConstructor
public class CommentController {

	private final CommentService commentService;

	@UserOnly
	@PostMapping
	public CommonResponseEntity<Void> createComment(
		@PathVariable long reviewId,
		@AuthenticatedUser LoginUser loginUser,
		@RequestBody CommentCreateRequest commentCreateRequest
	) {
		commentService.createComment(reviewId, loginUser, commentCreateRequest);

		return new CommonResponseEntity<>(COMMENT_CREATE_SUCCESS, HttpStatus.CREATED);
	}

	@UserOnly
	@PutMapping("/{commentId}")
	public CommonResponseEntity<Void> modifyComment(
		@PathVariable long reviewId,
		@PathVariable long commentId,
		@AuthenticatedUser LoginUser loginUser,
		@RequestBody CommentModifyRequest commentModifyRequest
	) {
		commentService.modifyComment(reviewId, commentId, loginUser, commentModifyRequest);

		return new CommonResponseEntity<>(COMMENT_MODIFY_SUCCESS, HttpStatus.OK);
	}

	@UserOnly
	@DeleteMapping("/{commentId}")
	public CommonResponseEntity<Void> removeComment(
		@PathVariable long reviewId,
		@PathVariable long commentId,
		@AuthenticatedUser LoginUser loginUser
	) {
		commentService.removeComment(reviewId, commentId, loginUser);

		return new CommonResponseEntity<>(COMMENT_REMOVE_SUCCESS, HttpStatus.OK);
	}

	@GetMapping
	public CommonResponseEntity<CommentSearchPagingResponse> searchComment(
		@PathVariable long reviewId,
		@AuthenticatedUser LoginUser loginUser,
		@CursorPageable CursorPageRequest pagingRequest
	) {
		return new CommonResponseEntity<>(
			COMMENT_SEARCH_SUCCESS,
			commentService.searchCommentsByReviewIdWithPaging(reviewId, loginUser, pagingRequest),
			HttpStatus.OK);
	}
}
