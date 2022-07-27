package com.jjikmuk.sikdorak.review.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewVisibilityTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {

        @Nested
        @DisplayName("만약 유효한 값이 들어온다면")
        class Context_with_valid_reviewVisibility {

            @ParameterizedTest
            @ValueSource(strings = {"PUBLIC", "PROTECTED", "PRIVATE", "puBlic", "Protected", "privatE"})
            @DisplayName("ReviewVisibility enum 을 반환한다")
            void It_returns_a_object(String visibility) {
                ReviewVisibility reviewVisibility = ReviewVisibility.create(visibility);

                assertThat(reviewVisibility.name()).isEqualTo(visibility.toUpperCase());
            }
        }
    }
}