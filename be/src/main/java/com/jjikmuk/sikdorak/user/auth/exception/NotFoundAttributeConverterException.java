package com.jjikmuk.sikdorak.user.auth.exception;

public class NotFoundAttributeConverterException extends RuntimeException {

    public NotFoundAttributeConverterException() {
        super("OAuth 플랫폼에 적합한 Converter를 찾지 못했습니다.");
    }
}
