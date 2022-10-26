package com.jjikmuk.sikdorak.common;

public enum ResponseCodeAndMessages implements CodeAndMessages {

    // Review
    REVIEW_CREATE_SUCCESS("T-R001", "리뷰 생성 성공했습니다."),
    REVIEW_MODIFY_SUCCESS("T-R002", "리뷰 수정 성공했습니다."),
    REVIEW_REMOVE_SUCCESS("T-R003", "리뷰 삭제 성공했습니다."),
    REVIEW_SEARCH_DETAIL_SUCCESS("T-R004", "리뷰 상세 조회 성공했습니다."),
    REVIEWS_FEED_SUCCESS("T-R005", "리뷰 피드 목록 조회 성공했습니다."),
    REVIEW_LIKE_SUCCESS("T-R006", "리뷰 좋아요 성공했습니다."),
    REVIEW_UNLIKE_SUCCESS("T-R007", "리뷰 좋아요 취소 성공했습니다."),

    // OAuth
    LOGIN_SUCCESS("T-O001", "로그인에 성공했습니다."),
    UPDATE_ACCESS_TOKEN_SUCCESS("T-O002", "액세스 토큰 재발급에 성공했습니다."),

    // Store
    STORE_SEARCH_SUCCESS("T-S001", "가게 목록 조회에 성공했습니다."),
    STORE_CREATE_SUCCESS("T-S002", "가게 등록에 성공했습니다."),
    STORE_MODIFY_SUCCESS("T-S003", "가게 수정에 성공했습니다."),
    STORE_REMOVE_SUCCESS("T-S004", "가게 삭제에 성공했습니다."),
    STORE_SEARCH_BY_RADIUS_SUCCESS("T-S005", "위치기반 가게 목록 조회에 성공했습니다."),
    STORE_VERIFY_OR_SAVE_RESPONSE("T-S006", "가게 등록 검증에 성공했습니다."),
    STORE_SEARCH_DETAIL_SUCCESS("T-S007", "가게 상세 조회에 성공했습니다."),
    STORE_SEARCH_REVIEW_SUCCESS("T-S008", "가게 리뷰 조회에 성공했습니다."),

    //User
    USER_MODIFY_SUCCESS("T-U001", "유저 프로필 수정에 성공했습니다."),
    USER_FOLLOW_SUCCESS("T-U002", "유저 팔로우에 성공했습니다."),
    USER_UNFOLLOW_SUCCESS("T-U003", "유저 언팔로우에 성공했습니다."),
    USER_SEARCH_REVIEWS_SUCCESS("T-U004", "유저 리뷰 검색 성공했습니다."),
    USER_SEARCH_PROFILE_SUCCESS("T-U005", "유저 프로필 조회에 성공했습니다."),
    USER_DELETE_SUCCESS("T-U006", "유저 정보 삭제에 성공했습니다."),
    USER_SEARCH_FOLLOWERS_SUCCESS("T-U007", "유저 팔로워 목록 조회에 성공했습니다."),
    USER_SEARCH_FOLLOWINGS_SUCCESS("T-U008", "유저 팔로잉 목록 조회에 성공했습니다."),
    USER_SEARCH_SUCCESS("T-U009", "유저 검색에 성공했습니다."),
    USER_REVIEW_SEARCH_BY_RADIUS_SUCCESS("T-U010", "위치기반 유저 리뷰목록 조회에 성공했습니다"),

    // Comment
    COMMENT_CREATE_SUCCESS("T-C001", "댓글 작성에 성공했습니다."),
    COMMENT_MODIFY_SUCCESS("T-C002", "댓글 수정에 성공했습니다."),
    COMMENT_REMOVE_SUCCESS("T-C003", "댓글 삭제에 성공했습니다."),
    COMMENT_SEARCH_SUCCESS("T-C004", "댓글 조회에 성공했습니다."),

    // ETC
    SYSTEMINFO_SEARCH_API_DOCS_INFO("T-S001", "API 문서 코드/메세지 검색 성공했습니다."),
	IMAGES_UPLOAD_PRESIGNED_URL_CREATE_SUCCESS("T-I001", "업로드 용도의 S3 PreSigned URL이 생성에 성공했습니다.");


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
