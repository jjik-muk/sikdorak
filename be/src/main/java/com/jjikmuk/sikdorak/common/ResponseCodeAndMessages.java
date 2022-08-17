package com.jjikmuk.sikdorak.common;

public enum ResponseCodeAndMessages implements CodeAndMessages {

    // Review
    REVIEW_CREATE_SUCCESS("T-R001", "리뷰 생성 성공했습니다."),
    REVIEW_MODIFY_SUCCESS("T-R002", "리뷰 수정 성공했습니다."),
    REVIEW_REMOVE_SUCCESS("T-R003", "리뷰 삭제 성공했습니다."),

    // OAuth
    LOGIN_SUCCESS("T-O001", "로그인에 성공했습니다."),
    UPDATE_ACCESS_TOKEN_SUCCESS("T-O002", "액세스 토큰 재발급에 성공했습니다."),

    // Store
    STORE_SEARCH_SUCCESS("T-S001", "가게 목록 조회에 성공했습니다."),
    STORE_CREATE_SUCCESS("T-S002", "가게 등록에 성공했습니다."),
    STORE_MODIFY_SUCCESS("T-S003", "가게 수정에 성공했습니다."),
    STORE_REMOVE_SUCCESS("T-S004", "가게 삭제에 성공했습니다."),

    //User
    USER_MODIFY_SUCCESS("T-U001", "유저 프로필 수정에 성공했습니다."),
    USER_FOLLOW_SUCCESS("T-U002", "유저 팔로우에 성공했습니다."),
    USER_UNFOLLOW_SUCCESS("T-U003", "유저 언팔로우에 성공했습니다."),
    USER_SEARCH_REVIEWS_SUCCESS("T-U004", "유저 리뷰 검색 성공했습니다."),
    USER_SEARCH_PROFILE_SUCCESS("T-U005", "유저 프로필 조회에 성공했습니다."),

    // ETC
    SYSTEMINFO_SEARCH_API_DOCS_INFO("T-S001", "API 문서 코드/메세지 검색 성공했습니다.");

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
