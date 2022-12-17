package com.jjikmuk.sikdorak.ratelimit.exception;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import org.springframework.http.HttpStatus;

public class ApiLimitExceededException extends SikdorakRuntimeException {

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.TOO_MANY_REQUESTS;
	}
}
