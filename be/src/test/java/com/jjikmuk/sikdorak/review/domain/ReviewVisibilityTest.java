package com.jjikmuk.sikdorak.review.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.jjikmuk.sikdorak.user.user.domain.RelationType;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class ReviewVisibilityTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

		@Nested
		@DisplayName("만약 유효한 값이 들어온다면")
		class Context_with_valid_reviewVisibility {

			@ParameterizedTest
			@ValueSource(strings = {"PUBLIC", "PROTECTED", "PRIVATE", "puBlic", "Protected",
				"privatE"})
			@DisplayName("ReviewVisibility enum 을 반환한다")
			void It_returns_a_object(String visibility) {
				ReviewVisibility reviewVisibility = ReviewVisibility.create(visibility);

				assertThat(reviewVisibility.name()).isEqualTo(visibility.toUpperCase());
			}
		}
	}

	@Nested
	@DisplayName("isRead 메서드")
	class Describe_isRead {

		@Nested
		@DisplayName("만약 visibility의 모든 경우와 RelationType의 모든 경우가 주어진다면")
		class Context_with_relationType_with_public {

			@ParameterizedTest(name = "visibility = {0}, relationType = {1}, expected = {2}")
			@MethodSource("provideContentAllCase")
			void It_returns_a_true(ReviewVisibility visibility, RelationType relationType, boolean expected) {
				assertThat(visibility.isRead(relationType)).isEqualTo(expected);
			}

			private static Stream<Arguments> provideContentAllCase() {
				return Stream.of(
					Arguments.of(ReviewVisibility.PUBLIC, RelationType.SELF, true),
					Arguments.of(ReviewVisibility.PROTECTED, RelationType.SELF, true),
					Arguments.of(ReviewVisibility.PRIVATE, RelationType.SELF, true),
					Arguments.of(ReviewVisibility.PUBLIC, RelationType.CONNECTION, true),
					Arguments.of(ReviewVisibility.PROTECTED, RelationType.CONNECTION, true),
					Arguments.of(ReviewVisibility.PRIVATE, RelationType.CONNECTION, false),
					Arguments.of(ReviewVisibility.PUBLIC, RelationType.DISCONNECTION, true),
					Arguments.of(ReviewVisibility.PROTECTED, RelationType.DISCONNECTION, false),
					Arguments.of(ReviewVisibility.PRIVATE, RelationType.DISCONNECTION, false)
				);
			}
		}

	}

}
