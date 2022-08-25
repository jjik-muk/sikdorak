package com.jjikmuk.sikdorak.comment.controller;

import com.jjikmuk.sikdorak.comment.controller.request.CommentCreateRequest;
import com.jjikmuk.sikdorak.comment.controller.request.CommentModifyRequest;
import com.jjikmuk.sikdorak.comment.service.CommentService;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

		return new CommonResponseEntity<>(ResponseCodeAndMessages.COMMENT_CREATE_SUCCESS,
			HttpStatus.CREATED);
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

		return new CommonResponseEntity<>(ResponseCodeAndMessages.COMMENT_MODIFY_SUCCESS,
			HttpStatus.OK);
	}
}
