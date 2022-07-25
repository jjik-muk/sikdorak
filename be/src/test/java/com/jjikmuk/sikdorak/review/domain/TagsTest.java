package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidTagsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Tags 클래스")
class TagsTest {

    @Nested
    @DisplayName("생성자")
    class Describe_constructor {
        @Nested
        @DisplayName("만약 태그 개수가 0개 ~ 30개 이하로 주어진다면")
        class Context_with_valid_Tags {

            @ParameterizedTest
            @ValueSource(ints = {0, 15, 30})
            @DisplayName("Tags 객체를 반환한다.")
            void It_returns_a_object(int length) {
                List<String> limitTags = new ArrayList<>();
                for (int i = 0; i < length; i++) {
                    limitTags.add("tag" + i);
                }

                Tags tags = new Tags(limitTags);

                assertThat(tags.size()).isEqualTo(length);
            }
        }

        @Nested
        @DisplayName("만약 태그 개수가 30개 초과로 주어진다면")
        class Context_with_invalid_Tags {

            @Test
            @DisplayName("예외를 발생시킨다.")
            void It_throws_exception() {
                List<String> limitTags = new ArrayList<>();

                for (int i = 0; i < 32; i++) {
                    limitTags.add("tag" + i);
                }

                assertThatThrownBy(()-> new Tags(limitTags))
                        .isInstanceOf(InvalidTagsException.class);
            }
        }

        @Nested
        @DisplayName("만약 태그 중 중복된 태그가 있다면")
        class Context_with_duplicated_Tags {

            @Test
            @DisplayName("중복된 항목을 제거한 Tags 객체를 반환한다.")
            void It_throws_exception() {
                List<String> duplicatedTags = List.of("맛집", "맛집", "맛집", "꿀맛");

                Tags tags = new Tags(duplicatedTags);

                assertThat(tags.size()).isEqualTo(2);
            }
        }
    }
}
