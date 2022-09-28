package com.jjikmuk.sikdorak.user.auth.exception;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends SikdorakRuntimeException {

    public InvalidTokenException() {
    }

    public InvalidTokenException(Throwable cause) {
        super(cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
