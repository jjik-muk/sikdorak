package com.jjikmuk.sikdorak.comment.domain;

import com.jjikmuk.sikdorak.comment.exception.InvalidCommentContentException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommentContent {

	private static final int LIMIT_LENGTH = 500;

	@Column(length = LIMIT_LENGTH)
	private String reviewContent;

	public CommentContent(String commentContent) {
		if (Objects.isNull(commentContent) ||
			commentContent.isBlank() ||
			commentContent.length() > LIMIT_LENGTH) {
			throw new InvalidCommentContentException();
		}

		this.reviewContent = commentContent;
	}
}
