package com.jjikmuk.sikdorak.unittest.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.jjikmuk.sikdorak.comment.domain.CommentContent;
import com.jjikmuk.sikdorak.comment.exception.InvalidCommentContentException;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

@DisplayName("단위 : CommentContent 클래스")
class CommentContentTest {

	@Nested
	@DisplayName("생성자")
	class Describe_constructor {

	    @Nested
	    @DisplayName("만약 500자 이내의 댓글 내용이 주어진다면")
	    class Context_with_valid_content_length {

	        @ParameterizedTest
	        @MethodSource("provideValidContents")
	        @DisplayName("댓글 CommentContent 객체가 생성된다.")
	        void It_create_object(String content) {
				CommentContent commentContent = new CommentContent(content);

				assertThat(commentContent.getReviewContent()).isEqualTo(content);
	        }

		    public static Stream<Arguments> provideValidContents() {
			    String repeat = "1".repeat(499);

			    return Stream.of(
				    Arguments.of("1"),
				    Arguments.of(repeat)
			    );
		    }
	    }

		@Nested
		@DisplayName("만약 유효하지 않은 댓글 내용이 주어진다면")
		class Context_with_invalid_content_length {

			@ParameterizedTest
			@NullSource
			@MethodSource("provideInvalidContents")
			@DisplayName("예외를 발생시킨다.")
			void It_throw_exception(String content) {

				assertThatThrownBy(() -> new CommentContent(content))
					.isInstanceOf(InvalidCommentContentException.class);
			}

			public static Stream<Arguments> provideInvalidContents() {
				String repeat = "1".repeat(501);

				return Stream.of(
					Arguments.of(""),
					Arguments.of(repeat)
				);
			}
		}
	}
}
