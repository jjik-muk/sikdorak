package com.jjikmuk.sikdorak.unittest.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.review.command.domain.ReviewContent;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewContentException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : ReviewContent 클래스")
class ReviewContentTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

	    @Nested
	    @DisplayName("만약 정상적인 1~500자 이하의 content가 주어진다면")
	    class Context_with_valid_content {

			@ParameterizedTest
			@ValueSource(strings = {"1", "content"})
			@DisplayName("ReviewContent 객체를반환한다")
			void It_returns_a_object(String content) {
				ReviewContent reviewContent = new ReviewContent(content);

				assertThat(reviewContent.getReviewContent()).isEqualTo(content);
			}
	    }

		@Nested
		@DisplayName("만약 유효하지 않은 content가 주어진다면")
		class Context_with_invalid_content {

			@ParameterizedTest
			@NullSource
			@MethodSource("provideContentForIsNullAndEmptyAnd500char")
			@DisplayName("예외를 발생시킨다.")
			void It_throws_exception(String content) {
				assertThatThrownBy(() -> new ReviewContent(content))
					.isInstanceOf(InvalidReviewContentException.class);
			}

			private static Stream<Arguments> provideContentForIsNullAndEmptyAnd500char() {
				String content = "a";
				return Stream.of(
					Arguments.of(""),
					Arguments.of(" "),
					Arguments.of("\t"),
					Arguments.of("\n"),
					Arguments.of(content.repeat(501))
				);
			}
		}


	}



}
