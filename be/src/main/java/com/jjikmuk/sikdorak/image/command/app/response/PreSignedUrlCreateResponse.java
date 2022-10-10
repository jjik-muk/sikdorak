package com.jjikmuk.sikdorak.image.command.app.response;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

public record PreSignedUrlCreateResponse(
	@NotEmpty @URL String preSignedUrl,
	@NotEmpty String fileName) {

}

