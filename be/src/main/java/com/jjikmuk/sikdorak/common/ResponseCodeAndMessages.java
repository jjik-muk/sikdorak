package com.jjikmuk.sikdorak.common;

public enum ResponseCodeAndMessages implements CodeAndMessages {

    REVIEW_CREATED_SUCCESS("T-R001", "리뷰 생성 성공했습니다."),

    // OAuth
    LOGIN_SUCCESS("T-O001", "로그인에 성공했습니다."),
    UPDATE_ACCESS_TOKEN_SUCCESS("T-O002", "액세스 토큰 재발급에 성공했습니다."),

    // Store
    STORE_SEARCH_SUCCESS("T-S001", "가게 목록 조회에 성공했습니다."),
    STORE_CREATE_SUCCESS("T-S002", "가게 등록에 성공했습니다."),
    STORE_MODIFY_SUCCESS("T-S003", "가게 수정에 성공했습니다.");

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
