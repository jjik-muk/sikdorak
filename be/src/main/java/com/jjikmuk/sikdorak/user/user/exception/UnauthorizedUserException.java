package com.jjikmuk.sikdorak.user.user.exception;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import org.springframework.http.HttpStatus;

public class UnauthorizedUserException extends SikdorakRuntimeException {

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.UNAUTHORIZED;
	}
}
