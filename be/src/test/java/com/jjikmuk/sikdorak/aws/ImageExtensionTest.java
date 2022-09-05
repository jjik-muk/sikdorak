package com.jjikmuk.sikdorak.aws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.aws.service.ImageExtension;
import com.jjikmuk.sikdorak.aws.service.exception.InvalidImagesExtensionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("ImageExtensionTest 클래스")
class ImageExtensionTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

		@Nested
		@DisplayName("만약 이미지 확장자(jpg, jpeg, png) 문자열이 주어진다면")
		class Context_with_valid_paramegers {

			@ParameterizedTest
			@CsvSource(value = {"'jpg', '.jpg'", "'png', '.png'", "'jpeg', '.jpeg'",
				"'JPG','.jpg'", "'Png', '.png'", "'jpEG', '.jpeg'"})

			@DisplayName("enum을 반환한다")
			void It_returns_a_enum(String extension, String expected) {
				ImageExtension imageExtension = ImageExtension.create(extension);

				assertThat(imageExtension.getExtension()).isEqualTo(expected);
			}
		}

		@Nested
		@DisplayName("만약 이미지 확장자가 아닌 문자열이 주어진다면")
		class Context_with_invalid_paramegers {

			@ParameterizedTest
			@NullAndEmptySource
			@ValueSource(strings = {"out", "java", "class", "1234"})
			@DisplayName("예외를 발생시킨다")
			void It_throws_error(String extension) {
				assertThatThrownBy(() ->
					ImageExtension.create(extension))
					.isInstanceOf(InvalidImagesExtensionException.class);
			}
		}

	}
}

