package com.jjikmuk.sikdorak.comment.controller;

import com.jjikmuk.sikdorak.comment.controller.request.CommentCreateRequest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reviews/{reviewId}/comments")
public class CommentController {

	@UserOnly
	@PostMapping()
	public CommonResponseEntity<Void> createComment(
		@PathVariable long reviewId,
		@AuthenticatedUser LoginUser loginUser,
		@RequestBody CommentCreateRequest commentCreateRequest
	) {


		return new CommonResponseEntity(ResponseCodeAndMessages.COMMENT_CREATE_SUCCESS, HttpStatus.CREATED);
	}
}
