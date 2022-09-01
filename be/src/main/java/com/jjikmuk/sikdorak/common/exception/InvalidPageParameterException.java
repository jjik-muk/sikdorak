package com.jjikmuk.sikdorak.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidPageParameterException extends SikdorakRuntimeException {

	public InvalidPageParameterException() {
	}

	public InvalidPageParameterException(Throwable cause) {
		super(cause);
	}

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
