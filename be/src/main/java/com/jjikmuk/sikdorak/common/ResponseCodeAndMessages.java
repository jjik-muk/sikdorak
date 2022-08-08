package com.jjikmuk.sikdorak.common;

public enum ResponseCodeAndMessages implements CodeAndMessages {

    REVIEW_CREATED("T-R001", "리뷰 생성 성공했습니다."),

    // Auth
    LOGIN_SUCCESS("T-A001", "로그인에 성공했습니다."),
    UPDATE_ACCESS_TOKEN_SUCCESS("T-A002", "액세스 토큰 재발급에 성공했습니다."),

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
