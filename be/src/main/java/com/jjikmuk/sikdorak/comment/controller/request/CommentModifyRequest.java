package com.jjikmuk.sikdorak.comment.controller.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentModifyRequest {

	@NotBlank
	@Size(min = 1, max = 500)
	private String content;

	public CommentModifyRequest(String content) {
		this.content = content;
	}
}
