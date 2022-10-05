package com.jjikmuk.sikdorak.aws.service.request;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PreSignedUrlCreateRequest {

	@NotEmpty
	@Pattern(regexp = "jpg\\|jpeg\\|png")
	private String extension;

	public PreSignedUrlCreateRequest(String extension) {
		this.extension = extension;
	}
}
