package com.jjikmuk.sikdorak.review.exception;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidReviewVisitedDateException extends SikdorakRuntimeException {

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.BAD_REQUEST;
	}
}
