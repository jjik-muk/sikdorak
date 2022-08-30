package com.jjikmuk.sikdorak.comment.controller.response;

import com.jjikmuk.sikdorak.comment.domain.Comment;
import com.jjikmuk.sikdorak.user.user.controller.response.UserSimpleProfileResponse;
import com.jjikmuk.sikdorak.user.user.domain.User;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CommentSearchResponse(
	@NotNull
	@Min(1)
	@Max(Long.MAX_VALUE)
	long id,

	@NotNull
	@Size(min = 1, max = 500)
	String content,

	UserSimpleProfileResponse author) {

	public static CommentSearchResponse of(Comment comment, User user) {
		return new CommentSearchResponse(
			comment.getId(),
			comment.getCommentContent(),
			UserSimpleProfileResponse.from(user)
		);
	}

}
