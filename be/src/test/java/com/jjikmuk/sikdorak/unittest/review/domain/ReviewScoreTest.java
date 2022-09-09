package com.jjikmuk.sikdorak.unittest.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.review.domain.ReviewScore;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewScoreException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : ReviewScore 클래스")
class ReviewScoreTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 reviewScore 가 1,2,3,4,5 중에서만 주어진다면 ")
        class Context_with_valid_reviewScore {

            @ParameterizedTest
            @ValueSource(floats = {1,2,3,4,5})
            @DisplayName("ReviewScore 객체를 반환한다")
            void It_returns_a_object(Float score) {
                ReviewScore reviewScore = new ReviewScore(score);

                assertThat(reviewScore.getReviewScore()).isEqualTo(score);
            }
        }

        @Nested
        @DisplayName("만약 reviewScore 가 1,2,3,4,5 이외의 값이 주어진다면 ")
        class Context_with_invalid_reviewScore {

            @ParameterizedTest
            @NullSource
            @ValueSource(floats = {-1, 0, 1.1f, 6})
            @DisplayName("예외를 발생시킨다")
            void It_throws_exception(Float score) {
                assertThatThrownBy(() -> new ReviewScore(score))
                        .isInstanceOf(InvalidReviewScoreException.class);
            }
        }
    }
}
