package com.jjikmuk.sikdorak.aws.exception;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidImagesExtensionException extends
	SikdorakRuntimeException {

	public InvalidImagesExtensionException(Throwable cause) {
		super(cause);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}

}
