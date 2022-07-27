package com.jjikmuk.sikdorak.store.exception;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import org.springframework.http.HttpStatus;

public class StoreNotFoundException extends SikdorakRuntimeException {

	@Override
	public HttpStatus getHttpStatus() {
		return HttpStatus.NOT_FOUND;
	}
}
