package com.jjikmuk.sikdorak.comment.command.app.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequest {

	@NotBlank
	@Size(min = 1, max = 500)
	private String content;

	public CommentCreateRequest(String content) {
		this.content = content;
	}
}
