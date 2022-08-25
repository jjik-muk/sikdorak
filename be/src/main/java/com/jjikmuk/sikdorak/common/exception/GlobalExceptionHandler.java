package com.jjikmuk.sikdorak.common.exception;

import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SikdorakRuntimeException.class)
    public CommonResponseEntity<Void> handle(SikdorakRuntimeException exception) {
        return new CommonResponseEntity<>(exception.getCodeAndMessages(), exception.getHttpStatus());
    }

}
