package com.jjikmuk.sikdorak.unittest.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.review.command.domain.ReviewVisitedDate;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewVisitedDateException;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("단위 : ReviewVisitedDate 클래스")
class ReviewVisitedDateTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

		@Nested
		@DisplayName("만약 reviewVisitedDate가 현재 시간보다 이전 시간이 주어진다면 ")
		class Context_with_valid_reviewVisitedDate {

			@Test
			@DisplayName("ReviewVisitedDate 객체를 반환한다")
			void It_returns_a_object() {
				ReviewVisitedDate reviewVisitedDate = new ReviewVisitedDate(
					LocalDate.of(2022, 7, 20), LocalDate.of(2022, 7, 21));

				assertThat(reviewVisitedDate.getReviewVisitedDate()).isEqualTo(
					LocalDate.of(2022, 7, 20));
			}
		}

		@Nested
		@DisplayName("만약 reviewVisitedDate가 현재 시간과 동일한 시간이 주어진다면 ")
		class Context_with_same_reviewVisitedDate {

			@Test
			@DisplayName("ReviewVisitedDate 객체를 반환한다")
			void It_returns_a_object() {
				ReviewVisitedDate reviewVisitedDate = new ReviewVisitedDate(
					LocalDate.of(2022, 7, 21), LocalDate.of(2022, 7, 21));

				assertThat(reviewVisitedDate.getReviewVisitedDate()).isEqualTo(
					LocalDate.of(2022, 7, 21));
			}
		}

		@Nested
		@DisplayName("만약 reviewVisitedDate가 유효하지 않은 시간이 주어진다면 ")
		class Context_with_invalid_reviewVisitedDate {

			@ParameterizedTest
			@MethodSource("provideLocalDates")
			@DisplayName("예외를 발생시킨다")
			void It_throws_exception(LocalDate reviewVisitedDate, LocalDate currentDate) {
				assertThatThrownBy(() -> new ReviewVisitedDate(reviewVisitedDate, currentDate))
					.isInstanceOf(InvalidReviewVisitedDateException.class);
			}

			private static Stream<Arguments> provideLocalDates() {
				return Stream.of(
					Arguments.of(null, LocalDate.of(2021,12,31)),
					Arguments.of(LocalDate.of(2022,1,1), null),
					Arguments.of(LocalDate.of(2022,1,1), LocalDate.of(2021,12,31))
				);
			}
		}
	}




}

