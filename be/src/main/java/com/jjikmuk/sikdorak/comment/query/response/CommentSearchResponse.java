package com.jjikmuk.sikdorak.comment.query.response;

import com.jjikmuk.sikdorak.comment.command.domain.Comment;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.query.response.UserSimpleProfileResponse;
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
