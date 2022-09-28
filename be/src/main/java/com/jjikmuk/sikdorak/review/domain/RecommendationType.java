package com.jjikmuk.sikdorak.review.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 리뷰 추천목록 조회 시 정렬 조건.
 *
 * @deprecated (서버에서 자체적으로 추천 조건을 설정하도록 수정하도록 변경됨)
 */
@Deprecated(since = "1.0", forRemoval = true)
@Getter
@NoArgsConstructor
public enum RecommendationType {

    /**
     * 작성일 기준 추천.
     *
     * @deprecated (서버에서 자체적으로 추천 조건을 설정하도록 수정하도록 변경됨)
     */
    @Deprecated(since = "1.0", forRemoval = true)
    TIME("created_at"),

    /**
     * 좋아요 개수 기준 추천.
     *
     * @deprecated (서버에서 자체적으로 추천 조건을 설정하도록 수정하도록 변경됨)
     */
    @Deprecated(since = "1.0", forRemoval = true)
    LIKE("like"),

    /**
     * 평점 기준 추천.
     *
     * @deprecated (서버에서 자체적으로 추천 조건을 설정하도록 수정하도록 변경됨)
     */
    @Deprecated(since = "1.0", forRemoval = true)
    SCORE("review_score");

    private String orderType;

    RecommendationType(String orderType) {
        this.orderType = orderType;
    }

}
