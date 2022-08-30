package com.jjikmuk.sikdorak.review.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 기존 : 클라이언트에게 추천 조건 설정 권한 제공
 수정 : 서버에서 자체적으로 추천 조건을 설정하도록 수정하도록 변경되서 Deprecated
 */

@Deprecated
@Getter
@NoArgsConstructor
public enum RecommendationType {

    TIME("created_at"),
    LIKE("like"),
    SCORE("review_score");

    private String orderType;

    RecommendationType(String orderType) {
        this.orderType = orderType;
    }

}
