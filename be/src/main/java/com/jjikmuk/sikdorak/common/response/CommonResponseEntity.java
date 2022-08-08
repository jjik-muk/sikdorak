package com.jjikmuk.sikdorak.common.response;

import com.jjikmuk.sikdorak.common.BaseResponse;
import com.jjikmuk.sikdorak.common.CodeAndMessages;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public class CommonResponseEntity<T> extends ResponseEntity<BaseResponse<T>> {
    public CommonResponseEntity(CodeAndMessages codeAndMessages, T data, HttpStatus httpStatus) {
        this(codeAndMessages, data, null, httpStatus);

    }

    public CommonResponseEntity(CodeAndMessages codeAndMessages, T data, MultiValueMap<String, String> headers,HttpStatus httpStatus) {
        super(new BaseResponse<>(codeAndMessages.getCode(), codeAndMessages.getMessage(), data),
            headers, httpStatus);
    }

}
