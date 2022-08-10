package com.jjikmuk.sikdorak.auth.exception;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends SikdorakRuntimeException {

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
