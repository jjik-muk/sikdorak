package com.jjikmuk.sikdorak.review.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Deprecated(since = "1.0", forRemoval = true)
@Getter
@NoArgsConstructor
public enum RecommendationType {

    /**
     * @deprecated (서버에서 자체적으로 추천 조건을 설정하도록 수정하도록 변경됨)
     */
    @Deprecated(since = "1.0", forRemoval = true)
    TIME("created_at"),
    @Deprecated(since = "1.0", forRemoval = true)
    LIKE("like"),
    @Deprecated(since = "1.0", forRemoval = true)
    SCORE("review_score");

    private String orderType;

    RecommendationType(String orderType) {
        this.orderType = orderType;
    }

}
