package com.jjikmuk.sikdorak.common.exception;

import org.springframework.http.HttpStatus;

public class UnsupportedParameterTypeException extends SikdorakRuntimeException {

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
