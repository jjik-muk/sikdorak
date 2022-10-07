package com.jjikmuk.sikdorak.unittest.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.jjikmuk.sikdorak.review.command.domain.Image;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewImageException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : Image 클래스")
class ImageTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

		@Nested
		@DisplayName("만약 정상적인 url 문자열이 주어진다면")
		class Context_with_valid_url_text {

			@ParameterizedTest
			@ValueSource(strings = {
				"http://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg",
				"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test2.jpg:80",
				"https://s3.ap-northeast-2.amazonaws.com/sikdorak/aasf/test.jpg",
			})
			@DisplayName("image 객체를 반환한다")
			void It_returns_a_object(String reviewImage) {
				Image image = new Image(reviewImage);

				assertThat(image.getUrl()).isEqualTo(reviewImage);
			}
		}

		@Nested
		@DisplayName("만약 비정상적인 url 문자열이 주어진다면")
		class Context_with_invalid_url_text {

			@ParameterizedTest
			@ValueSource(strings = {
				"htps://s3.ap-northeast-2.amazonaws/sikdorak/test.jpg",
				"htps://sikdorak/test.jpg",
				"https:/s3.ap-northeast-2.amazonaws.com//test2.jpg",
				"https:s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg",
			})
			@DisplayName("예외를 발생시킨다.")
			void It_returns_a_object(String reviewImage) {
				assertThatThrownBy(() -> new Image(reviewImage))
					.isInstanceOf(InvalidReviewImageException.class);
			}
		}
	}

}
