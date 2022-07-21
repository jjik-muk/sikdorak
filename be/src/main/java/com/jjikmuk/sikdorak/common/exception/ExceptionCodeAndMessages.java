package com.jjikmuk.sikdorak.common.exception;

import com.jjikmuk.sikdorak.common.CodeAndMessages;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewContentException;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewScoreException;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewVisibilityException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ExceptionCodeAndMessages implements CodeAndMessages {
    NOT_FOUND_ERROR_CODE("F-G001", "에러 코드를 찾을 수 없습니다.", NotFoundErrorCodeException.class),

    // Review
    INVALID_REVIEW_CONTENT("F-R001", "유효하지 않은 리뷰 컨텐츠 입니다.", InvalidReviewContentException.class),
    INVALID_REVIEW_SCORE("F-R002", "유효하지 않은 리뷰 평점 입니다.",InvalidReviewScoreException.class),
    INVALID_REVIEW_VISIBILITY("F-R003", "유효하지 않은 리뷰 공개 범위 입니다.",InvalidReviewVisibilityException .class);

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
