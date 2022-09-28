package com.jjikmuk.sikdorak.user.auth.exception;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends SikdorakRuntimeException {

    public ExpiredTokenException(Throwable cause) {
        super(cause);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
