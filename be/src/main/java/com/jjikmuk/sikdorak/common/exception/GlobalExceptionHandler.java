package com.jjikmuk.sikdorak.common.exception;

import com.jjikmuk.sikdorak.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SikdorakRuntimeException.class)
    public ResponseEntity<BaseResponse> handle(SikdorakRuntimeException exception) {
        ExceptionCodeAndMessages codeAndMessages = exception.getCodeAndMessages();

        return new ResponseEntity<>(
                new BaseResponse<>(codeAndMessages.getCode(), codeAndMessages.getMessage(), null),
                exception.getHttpStatus()
        );
    }

}
