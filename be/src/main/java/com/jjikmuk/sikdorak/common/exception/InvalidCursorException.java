package com.jjikmuk.sikdorak.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidCursorException extends SikdorakRuntimeException {

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
