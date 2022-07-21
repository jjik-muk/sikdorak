package com.jjikmuk.sikdorak.common.exception;

import com.jjikmuk.sikdorak.common.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<BaseResponse> handle(SikdorakRuntimeException exception) {
        ExceptionCodeAndMessages codeAndMessages = exception.getCodeAndMessages();

        return new ResponseEntity<>(
                new BaseResponse<>(codeAndMessages.getCode(), codeAndMessages.getMessage(), null),
                exception.getHttpStatus()
        );
    }

}
