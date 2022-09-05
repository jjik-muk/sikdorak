package com.jjikmuk.sikdorak.aws.controller.response;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public record PresignedUrlCreateResponse(
	@NotEmpty @URL String presignedUrl) {

}

