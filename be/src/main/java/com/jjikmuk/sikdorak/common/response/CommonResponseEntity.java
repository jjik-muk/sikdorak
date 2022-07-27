package com.jjikmuk.sikdorak.common.response;

import com.jjikmuk.sikdorak.common.BaseResponse;
import com.jjikmuk.sikdorak.common.CodeAndMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonResponseEntity<T> extends ResponseEntity<BaseResponse<T>> {
    public CommonResponseEntity(CodeAndMessages codeAndMessages, T data, HttpStatus httpStatus) {
        super(new BaseResponse<>(codeAndMessages.getCode(), codeAndMessages.getMessage(), data), httpStatus);
    }
}
