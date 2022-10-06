package com.jjikmuk.sikdorak.unittest.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.review.command.domain.Tag;
import com.jjikmuk.sikdorak.review.exception.InvalidTagException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : Tag 클래스")
class TagTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

		@Nested
		@DisplayName("만약 태그가 0~50자 이하의 문자열로 주어진다면")
		class Context_with_valid_Tag {

			@ParameterizedTest
			@MethodSource("provideValidTag")
			@DisplayName("Tag 객체를 반환한다.")
			void It_returns_a_object(String rawTag, String expected) {
				Tag tag = new Tag(rawTag);

				assertThat(tag.getText()).isEqualTo(expected);
			}

			private static Stream<Arguments> provideValidTag() {
				String content = "a";
				return Stream.of(
					Arguments.of("맛집", "맛집"),
					Arguments.of("꿀맛집", "꿀맛집"),
					Arguments.of(content.repeat(50), content.repeat(50))
				);
			}
		}

		@Nested
		@DisplayName("만약 태그가 대소문자 구분없는 문자열이 주어진다면")
		class Context_with_upper_and_lower_Tag {

			@ParameterizedTest
			@ValueSource(strings = {"good", "Good", "GoOd"})
			@DisplayName("소문자로 변환한 Tag 객체를 반환한다.")
			void It_returns_a_object(String rawTag) {
				Tag tag = new Tag(rawTag);

				assertThat(tag.getText()).isEqualTo("good");
			}
		}

		@Nested
		@DisplayName("만약 유효하지 않은 태그가 주어진다면")
		class Context_with_invalid_Tag {

			@ParameterizedTest
			@NullAndEmptySource
			@MethodSource("provideInvalidTag")
			@DisplayName("예외를 발생시킨다.")
			void It_throws_exception(String rawTag) {
				assertThatThrownBy(() -> new Tag(rawTag))
					.isInstanceOf(InvalidTagException.class);
			}


			private static Stream<Arguments> provideInvalidTag() {
				String content = "a";
				return Stream.of(
					Arguments.of("중간 공백"),
					Arguments.of(" 공 백"),
					Arguments.of("중간\t공백"),
					Arguments.of(content.repeat(51)),
					Arguments.of("특수문자#"),
					Arguments.of("특수문자!"),
					Arguments.of("특수문자%$"),
					Arguments.of("특수문자()"),
					Arguments.of("특수문자+")
				);
			}
		}
	}

}
