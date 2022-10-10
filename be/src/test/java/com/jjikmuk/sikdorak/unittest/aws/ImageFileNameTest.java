package com.jjikmuk.sikdorak.unittest.aws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.common.exception.SikdorakRuntimeException;
import com.jjikmuk.sikdorak.image.command.domain.ImageFileName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : ImageExtension 클래스")
class ImageFileNameTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

	    @Nested
	    @DisplayName("만약 정상적인 URL String이 주어진다면")
	    class Context_with_valid_URL_String {

			@Test
	        @DisplayName("ImageFileName 객체를 반환한다")
	        void It_returns_a_valid_ImageFileName_object() {
		        String imageOriginUrl = "https://sikdorak-images.s3.ap-northeast-2.amazonaws.com/origin/abcde-1234-testimage-jjikmuk.png";
		        ImageFileName imageFileName = new ImageFileName(imageOriginUrl);

		        assertThat(imageFileName.getFileName()).isEqualTo(
			        "abcde-1234-testimage-jjikmuk.png");
	        }
	    }

		@Nested
		@DisplayName("만약 비정상적인 URL String이 주어진다면")
		class Context_with_invalid_URL_String {

			@ParameterizedTest
			@NullAndEmptySource
			@ValueSource(strings = {"localhost/test", "http://localhost/test", "https://localhost/test", "https://sikdorak-images.s3.ap-northeast-2.amazonaws.com/origin/abcde-1234-testimage-jjikmuk.mp4"})
			@DisplayName("예외를 발생시킨다.")
			void It_returns_a_valid_ImageFileName_object(String imageOriginUrl) {
				assertThatThrownBy(() ->
					new ImageFileName(imageOriginUrl))
					.isInstanceOf(SikdorakRuntimeException.class);
			}
		}

	}
}
