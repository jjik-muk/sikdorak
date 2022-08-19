package com.jjikmuk.sikdorak.common.exception;

import com.jjikmuk.sikdorak.common.CodeAndMessages;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewContentException;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewScoreException;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewVisibilityException;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewVisitedDateException;
import com.jjikmuk.sikdorak.review.exception.InvalidTagException;
import com.jjikmuk.sikdorak.review.exception.InvalidTagsException;
import com.jjikmuk.sikdorak.review.exception.NotFoundReviewException;
import com.jjikmuk.sikdorak.store.exception.InvalidAddressException;
import com.jjikmuk.sikdorak.store.exception.InvalidContactNumberException;
import com.jjikmuk.sikdorak.store.exception.InvalidStoreLocationException;
import com.jjikmuk.sikdorak.store.exception.InvalidStoreNameException;
import com.jjikmuk.sikdorak.store.exception.NotFoundStoreException;
import com.jjikmuk.sikdorak.user.auth.exception.InvalidTokenException;
import com.jjikmuk.sikdorak.user.auth.exception.NeedLoginException;
import com.jjikmuk.sikdorak.user.auth.exception.OAuthServerException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateFollowingException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateUserException;
import com.jjikmuk.sikdorak.user.user.exception.DuplicateSendAcceptUserException;
import com.jjikmuk.sikdorak.user.user.exception.InvalidFollowersException;
import com.jjikmuk.sikdorak.user.user.exception.InvalidFollowingException;
import com.jjikmuk.sikdorak.user.user.exception.InvalidUserEmailException;
import com.jjikmuk.sikdorak.user.user.exception.InvalidUserNicknameException;
import com.jjikmuk.sikdorak.user.user.exception.InvalidUserProfileImageUrlException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundFollowException;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import com.jjikmuk.sikdorak.user.user.exception.UnauthorizedUserException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ExceptionCodeAndMessages implements CodeAndMessages {
    NOT_FOUND_ERROR_CODE("F-G001", "에러 코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),

    // Review
    INVALID_REVIEW_CONTENT("F-R001", "유효하지 않은 리뷰 컨텐츠 입니다.", InvalidReviewContentException.class),
    INVALID_REVIEW_SCORE("F-R002", "유효하지 않은 리뷰 평점 입니다.", InvalidReviewScoreException.class),
    INVALID_REVIEW_VISIBILITY("F-R003", "유효하지 않은 리뷰 공개 범위 입니다.", InvalidReviewVisibilityException .class),
    INVALID_REVIEW_VISITEDDATE("F-R004", "유효하지 않은 방문일자 입니다.", InvalidReviewVisitedDateException.class),
    INVALID_REVIEW_TAGS("F-R005", "유효하지 않은 태그들 입니다.", InvalidTagsException.class),
    INVALID_REVIEW_TAG("F-R006", "유효하지 않은 태그 입니다.", InvalidTagException.class),
    NOT_FOUND_STROE("F-R007", "리뷰를 찾을 수 없습니다.", NotFoundReviewException.class),

    // Store
    NOT_FOUND_STORE("F-S001", "Store Id를 찾을 수 없습니다.", NotFoundStoreException.class),
    INVALID_STORE_NAME("F-S002", "유효하지 않은 가게이름 입니다.",InvalidStoreNameException.class),
    INVALID_CONTACT_NUMER("F-S003", "유효하지 않은 연락처 입니다.", InvalidContactNumberException.class),
    INVALID_ADDRESS("F-S004", "유효하지 않은 연락처 입니다.", InvalidAddressException.class),
    INVALID_STORE_LOCATION("F-S005", "유효하지 않은 좌표 입니다.",InvalidStoreLocationException .class),

    // User
    DUPLICATE_USER("F-U001", "중복된 유저입니다.",DuplicateUserException.class),
    INVALID_USER_NIKCNAME("F-U002", "유효하지 않은 닉네임 입니다.", InvalidUserNicknameException.class),
    INVALID_USER_PROFILE_IMAGE("F-U003", "유효하지 않은 프로필 이미지 url의 형식입니다.", InvalidUserProfileImageUrlException.class),
    INVALID_USER_EMAIL("F-U004", "유효하지 않은 이메일 형식입니다.", InvalidUserEmailException.class),
    INVALID_FOLLOWERS("F-U005", "유효하지 않은 팔로워 목록입니다.", InvalidFollowersException.class),
    INVALID_FOLLOWINGS("F-U06", "유효하지 않은 팔로잉 목록입니다.", InvalidFollowingException.class),
    NOT_FOUND_USER("F-U007", "존재하지 않는 유저입니다.", NotFoundUserException.class),
    UNAUTHORIZED_USER("F-U008", "권한이 없는 유저입니다.", UnauthorizedUserException.class),
    DUPLICATE_SEND_ACCEPT_USER("F-U009", "팔로우 요청자와 대상자의 정보가 중복됩니다.", DuplicateSendAcceptUserException.class),
    DUPLICATE_FOLLOWING_USER("F-U0010", "이미 팔로우 된 유저입니다", DuplicateFollowingException.class),
    NOT_FOUND_FOLLOWING_USER("F-U011","존재하지 않는 팔로우 정보 입니다.", NotFoundFollowException.class),

    //OAuth
    FAILED_CONNECTION_WITH_OAUTH_SERVER("F-O001", "OAuth 서버와의 통신이 원할하지 않습니다.", OAuthServerException.class),
    INVALID_TOKEN("F-O002", "유효하지 않은 토큰입니다.", InvalidTokenException.class),
    NEED_LOGIN("F-O003", "로그인이 필요한 서비스입니다.", NeedLoginException.class);

    private final String code;

    private final String message;
    private final Class<? extends Exception> type;

    ExceptionCodeAndMessages(String code, String message, Class<? extends Exception> type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    public static ExceptionCodeAndMessages findByExceptionClass(Class<? extends Exception> type) {
        return Arrays.stream(values())
                .filter(codeAndMessage -> codeAndMessage.type.equals(type))
                .findAny()
                .orElseThrow(NotFoundErrorCodeException::new);
    }
}
