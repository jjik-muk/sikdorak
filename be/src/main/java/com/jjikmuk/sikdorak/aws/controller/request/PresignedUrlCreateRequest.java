package com.jjikmuk.sikdorak.aws.controller.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PresignedUrlCreateRequest {

	@NotEmpty
	@Pattern(regexp = ".jpg\\|.jpeg\\|.png")
	private String extension;

	public PresignedUrlCreateRequest(String extension) {
		this.extension = extension;
	}
}
