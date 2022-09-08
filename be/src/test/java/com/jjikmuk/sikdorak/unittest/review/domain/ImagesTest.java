package com.jjikmuk.sikdorak.unittest.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.jjikmuk.sikdorak.review.domain.Images;
import com.jjikmuk.sikdorak.review.exception.InvalidReviewImagesException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위 : Images 클래스")
class ImagesTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

		@Nested
		@DisplayName("만약 이미지 url 개수가 0 ~ 10개 이하로 주어진다면")
		class Context_with_valid_images {

			@ParameterizedTest
			@ValueSource(ints = {0, 5, 10})
			@DisplayName("images 객체를 반환한다.")
			void It_returns_a_object(int length) {
				List<String> limitImages = new ArrayList<>();
				for (int i = 0; i < length; i++) {
					limitImages.add("https://s3.ap-northeast-2.amazonaws.com/sikdorak/" + i);
				}

				Images images = new Images(limitImages);

				assertThat(images.size()).isEqualTo(length);
			}
		}

		@Nested
		@DisplayName("만약 image 리스트가 null로 주어진다면")
		class Context_with_null_image_list {

			@Test
			@DisplayName("비어있는 images 객체를 반환한다")
			void It_returns_a_empty_object() {
				Images images = new Images(null);

				assertThat(images.size()).isZero();
			}

		}

		@Nested
		@DisplayName("만약 image 리스트가 10개 초과로 주어진다면")
		class Context_with_images_size_greater_than_10 {

			@Test
			@DisplayName("예외를 발생시킨다")
			void It_throws_exception() {
				List<String> limitImages = new ArrayList<>();

				for (int i = 0; i < 11; i++) {
					limitImages.add("https://s3.ap-northeast-2.amazonaws.com/sikdorak/" + i);
				}

				assertThatThrownBy(() -> new Images(limitImages))
					.isInstanceOf(InvalidReviewImagesException.class);
			}
		}

		@Nested
		@DisplayName("만약 image 리스트가 중복으로 주어진다면")
		class Context_with_deplicated_images {

			@Test
			@DisplayName("중복 url을 제거한 images 객체를 반환한다.")
			void It_returns_a_object1() {
				List<String> deplicatedImages =
					List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/2");

				Images images = new Images(deplicatedImages);

				assertThat(images.size()).isEqualTo(2);
			}

			@Test
			@DisplayName("중복 url을 제거한 images 객체를 반환한다.")
			void It_returns_a_object2() {
				List<String> deplicatedImages =
					List.of("https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/1",
						"https://s3.ap-northeast-2.amazonaws.com/sikdorak/2");

				Images images = new Images(deplicatedImages);

				assertThat(images.size()).isEqualTo(2);
			}
		}
	}
}

