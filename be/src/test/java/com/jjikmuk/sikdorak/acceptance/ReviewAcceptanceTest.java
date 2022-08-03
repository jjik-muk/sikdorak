package com.jjikmuk.sikdorak.acceptance;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.fieldWithPathAndValidConstraints;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import com.jjikmuk.sikdorak.review.controller.request.ReviewInsertRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

/**
 * [x] 요청 텍스트가 유효하지 않은 경우(null, empty, 500자 넘는 경우) [x] 요청 식당 id가 유효하지 않은 경우(null, 0, -1, 등록되지 않은 값)
 * [x] 요청 방문일이 유효하지 않은 경우(미래 날짜, 유효하지 않은 날짜 형식) [x] 요청 평점이 유효하지 않은 경우(1,2,3,4,5 가 아닌 경우) [x] 요청 태그들이
 * 유효하지 않은 경우(공백 포함, 한글 영어 숫자 이외의 값, 50자 초과, 개수 30개 초과) [x] 요청 공개 범위가 유효하지 않은 경우(public, protected,
 * private 이외의 값, null, empty)
 *  TODO
 *  [ ] 요청 이미지가 유효하지 않은 경우(유효하지 않은 image 경로 (유효하지 않은 URL포맷, 관리하고 있는 s3 주소))
 */
public class ReviewAcceptanceTest extends InitAcceptanceTest {

	private static final Snippet REVIEW_INSERT_REQUEST = requestFields(
		fieldWithPathAndValidConstraints("reviewContent", ReviewInsertRequest.class)
			.type(JsonFieldType.STRING)
			.description("리뷰 내용"),
		fieldWithPathAndValidConstraints("storeId", ReviewInsertRequest.class)
			.type(JsonFieldType.NUMBER)
			.description("가게 아이디"),
		fieldWithPathAndValidConstraints("reviewScore", ReviewInsertRequest.class)
			.type(JsonFieldType.NUMBER)
			.description("리뷰 점수"),
		fieldWithPathAndValidConstraints("reviewVisibility", ReviewInsertRequest.class)
			.type(JsonFieldType.STRING)
			.description("리뷰 게시물의 공개 범위"),
		fieldWithPathAndValidConstraints("visitedDate", ReviewInsertRequest.class)
			.type(JsonFieldType.STRING)
			.description("가게 방문일"),
		fieldWithPathAndValidConstraints("tags", ReviewInsertRequest.class)
			.type(JsonFieldType.ARRAY)
			.description("리뷰를 표현하는 태그들"),
		fieldWithPathAndValidConstraints("images", ReviewInsertRequest.class)
			.type(JsonFieldType.ARRAY)
			.description("리뷰를 위한 사진 URL")
	);

	@Test
	@DisplayName("리뷰 생성 요청이 정상적인 경우라면 리뷰 생성 후 정상 상태 코드를 반환한다")
	void create_review_success() {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", "찐맛집입니다.");
		requestBody.put("storeId", savedStore.getId());
		requestBody.put("reviewScore", 4);
		requestBody.put("reviewVisibility", "public");
		requestBody.put("visitedDate", "2022-01-01");
		requestBody.put("tags", new String[]{"맛집", "꿀맛"});
		requestBody.put("images",
			new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given(this.spec)
			.filter(document(DEFAULT_RESTDOC_PATH, REVIEW_INSERT_REQUEST))
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)

			.when()
			.post("/api/reviews")

			.then()
			.statusCode(HttpStatus.CREATED.value());
	}

	@ParameterizedTest
	@MethodSource("provideContentForIsNullAndEmptyAnd500char")
	@DisplayName("리뷰 생성 요청에서 컨텐츠가 유효하지 않은 경우라면 에러 상태 코드를 반환한다")
	void create_review_content_invalid_failed(String content, int expectedStatusCode) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", content);
		requestBody.put("storeId", savedStore.getId());
		requestBody.put("reviewScore", 4);
		requestBody.put("reviewVisibility", "public");
		requestBody.put("visitedDate", "2022-01-01");
		requestBody.put("tags", new String[]{"맛집", "꿀맛"});
		requestBody.put("images",
			new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)

			.when()
			.post("/api/reviews")

			.then()
			.statusCode(expectedStatusCode)
			.body("code", equalTo(ExceptionCodeAndMessages.INVALID_REVIEW_CONTENT.getCode()));
	}

	@ParameterizedTest
	@MethodSource("provideStoreIdForIsNullAndZeroAndMinusAndUnregistered")
	@DisplayName("리뷰 생성 요청에서 식당 id가 유효하지 않은 경우 에러 상태코드를 반환한다")
	void create_review_storeId_invalid_failed(Long storeId, int expectedStatusCode) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", "찐맛집입니다.");
		requestBody.put("storeId", storeId);
		requestBody.put("reviewScore", 4);
		requestBody.put("reviewVisibility", "public");
		requestBody.put("visitedDate", "2022-01-01");
		requestBody.put("tags", new String[]{"맛집", "꿀맛"});
		requestBody.put("images",
			new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)

			.when()
			.post("/api/reviews")

			.then()
			.statusCode(expectedStatusCode)
			.body("code", equalTo(ExceptionCodeAndMessages.NOT_FOUND_STORE.getCode()));
	}

	@Test
	@DisplayName("리뷰 생성 요청에서 방문일이 유효하지 않은 경우 에러 상태코드를 반환한다")
	void create_review_visitedDate_invalid_failed() {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", "찐맛집입니다.");
		requestBody.put("storeId", savedStore.getId());
		requestBody.put("reviewScore", 4);
		requestBody.put("reviewVisibility", "public");
		requestBody.put("visitedDate", "9999-12-31");
		requestBody.put("tags", new String[]{"맛집", "꿀맛"});
		requestBody.put("images",
			new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)

			.when()
			.post("/api/reviews")

			.then()
			.log().all()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("code", equalTo(ExceptionCodeAndMessages.INVALID_REVIEW_VISITEDDATE.getCode()));
	}

	@ParameterizedTest
	@NullSource
	@ValueSource(floats = {0f, -1f, 1.1f, 6f})
	@DisplayName("리뷰 생성 요청에서 평점이 유효하지 않은 경우 에러 상태코드를 반환한다")
	void create_review_reviewScore_invalid_failed(Float reviewScore) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", "찐맛집입니다.");
		requestBody.put("storeId", savedStore.getId());
		requestBody.put("reviewScore", reviewScore);
		requestBody.put("reviewVisibility", "public");
		requestBody.put("visitedDate", "2022-01-01");
		requestBody.put("tags", new String[]{"맛집", "꿀맛"});
		requestBody.put("images",
			new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)

			.when()
			.post("/api/reviews")

			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("code", equalTo(ExceptionCodeAndMessages.INVALID_REVIEW_SCORE.getCode()));
	}

	@ParameterizedTest
	@MethodSource("provideReviewTagsForIsNullAndWhiteSpaceAndInvalidValues")
	@DisplayName("리뷰 생성 요청에서 태그들이 유효하지 않은 경우 에러 상태코드를 반환한다")
	void create_review_reviewTags_invalid_failed(List<String> reviewTags,
		String expectedErrorCode) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", "찐맛집입니다.");
		requestBody.put("storeId", savedStore.getId());
		requestBody.put("reviewScore", 3);
		requestBody.put("reviewVisibility", "public");
		requestBody.put("visitedDate", "2022-01-01");
		requestBody.put("tags", reviewTags);
		requestBody.put("images",
			new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)

			.when()
			.post("/api/reviews")

			.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("code", equalTo(expectedErrorCode));
	}

	@ParameterizedTest
	@NullAndEmptySource
	@ValueSource(strings = {"ppublic", "hello", "world"})
	@DisplayName("리뷰 생성 요청에서 공개 범위가 유효하지 않은 경우 에러 상태코드를 반환한다")
	void create_review_reviewVisibility_invalid_failed(String visibility) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", "찐맛집입니다.");
		requestBody.put("storeId", savedStore.getId());
		requestBody.put("reviewScore", 3);
		requestBody.put("reviewVisibility", visibility);
		requestBody.put("visitedDate", "2022-01-01");
		requestBody.put("tags", new String[]{"맛집", "꿀맛"});
		requestBody.put("images",
			new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)
			.log().all()

			.when()
			.post("/api/reviews")

			.then()
			.log().all()
			.statusCode(HttpStatus.BAD_REQUEST.value())
			.body("code", equalTo(ExceptionCodeAndMessages.INVALID_REVIEW_VISIBILITY.getCode()));
	}

	private static Stream<Arguments> provideContentForIsNullAndEmptyAnd500char() {
		String content = "a";
		return Stream.of(
			Arguments.of(null, HttpStatus.BAD_REQUEST.value()),
			Arguments.of("", HttpStatus.BAD_REQUEST.value()),
			Arguments.of(" ", HttpStatus.BAD_REQUEST.value()),
			Arguments.of("\t", HttpStatus.BAD_REQUEST.value()),
			Arguments.of("\n", HttpStatus.BAD_REQUEST.value()),
			Arguments.of(content.repeat(501), HttpStatus.BAD_REQUEST.value())
		);
	}

	private static Stream<Arguments> provideStoreIdForIsNullAndZeroAndMinusAndUnregistered() {
		long unregisteredStoreId = Long.MAX_VALUE;

		return Stream.of(
			Arguments.of(null, HttpStatus.NOT_FOUND.value()),
			Arguments.of(0L, HttpStatus.NOT_FOUND.value()),
			Arguments.of(-1L, HttpStatus.NOT_FOUND.value()),
			Arguments.of(unregisteredStoreId, HttpStatus.NOT_FOUND.value())
		);
	}

	private static Stream<Arguments> provideReviewTagsForIsNullAndWhiteSpaceAndInvalidValues() {
		String tempChar = "a";
		List<String> limitTags = new ArrayList<>();

		for (int i = 0; i < 32; i++) {
			limitTags.add("tag" + i);
		}

		return Stream.of(
			Arguments.of(null, ExceptionCodeAndMessages.INVALID_REVIEW_TAGS.getCode()),
			Arguments.of(List.of("맛집", ""), ExceptionCodeAndMessages.INVALID_REVIEW_TAG.getCode()),
			Arguments.of(List.of("맛집", "중간   공백"),
				ExceptionCodeAndMessages.INVALID_REVIEW_TAG.getCode()),
			Arguments.of(List.of("맛집", "중간\t공백"),
				ExceptionCodeAndMessages.INVALID_REVIEW_TAG.getCode()),
			Arguments.of(List.of("맛집", "중간\n공백"),
				ExceptionCodeAndMessages.INVALID_REVIEW_TAG.getCode()),
			Arguments.of(List.of("맛집", "특수문자#"),
				ExceptionCodeAndMessages.INVALID_REVIEW_TAG.getCode()),
			Arguments.of(List.of("맛집", "특수문자!"),
				ExceptionCodeAndMessages.INVALID_REVIEW_TAG.getCode()),
			Arguments.of(List.of("맛집", "특수문자*"),
				ExceptionCodeAndMessages.INVALID_REVIEW_TAG.getCode()),
			Arguments.of(List.of("맛집", tempChar.repeat(51)),
				ExceptionCodeAndMessages.INVALID_REVIEW_TAG.getCode()),
			Arguments.of(limitTags, ExceptionCodeAndMessages.INVALID_REVIEW_TAGS.getCode())
		);
	}

}
