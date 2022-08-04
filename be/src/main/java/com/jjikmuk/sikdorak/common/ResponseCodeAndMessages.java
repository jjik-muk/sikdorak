package com.jjikmuk.sikdorak.common;

public enum ResponseCodeAndMessages implements CodeAndMessages {

    REVIEW_CREATED("T-R001", "리뷰 생성 성공했습니다."),
    LOGIN_SUCCESS("T-L001", "로그인에 성공했습니다."),

    // Store
    STORE_FIND_SUCCESS("T-S001", "가게 목록 조회에 성공했습니다.");

    private final String code;
    private final String message;

    ResponseCodeAndMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
