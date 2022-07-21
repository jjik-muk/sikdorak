package com.jjikmuk.sikdorak.acceptance;

import static io.restassured.RestAssured.given;

import com.jjikmuk.sikdorak.store.domain.Store;
import com.jjikmuk.sikdorak.store.repository.StoreRepository;

import java.time.LocalDate;
import java.util.stream.Stream;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 *  [x] 요청 텍스트가 유효하지 않은 경우(null, empty, 500자 넘는 경우)
 *  [x] 요청 식당 id가 유효하지 않은 경우(null, 0, -1, 등록되지 않은 값)
 *  [ ] 요청 방문일이 유효하지 않은 경우(미래 날짜, 유효하지 않은 날짜 형식)
 *  [ ] 요청 평점이 유효하지 않은 경우(1,2,3,4,5 가 아닌 경우)
 *  [ ] 요청 태그들이 유효하지 않은 경우(공백 포함, 한글 영어 숫자 이외의 값, 50자 초과, 개수 30개 초과)
 *  [ ] 요청 공개 범위가 유효하지 않은 경우(public, protected, private 이외의 값, null, empty)
 */
public class ReviewAccecptanceTest extends InitAcceptanceTest {

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
		requestBody.put("images", new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)
			.log().all()

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
		requestBody.put("images", new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)

			.when()
			.post("/api/reviews")

			.then()
			.statusCode(expectedStatusCode);
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
		requestBody.put("images", new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header("Content-type", "application/json")
				.body(requestBody)

				.when()
				.post("/api/reviews")

				.then()
				.statusCode(expectedStatusCode);

	}

	@ParameterizedTest
	@CsvSource({
			"9999-12-31, 400",
			"02-01, 400"
	})
	@DisplayName("리뷰 생성 요청에서 방문일이 유효하지 않은 경우 에러 상태코드를 반환한다")
	// (미래 날짜, 유효하지 않은 날짜 형식)
	void create_review_visitedDate_invalid_failed(String localDate, int expectedStatusCode) {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", "찐맛집입니다.");
		requestBody.put("storeId", savedStore.getId());
		requestBody.put("reviewScore", 4);
		requestBody.put("reviewVisibility", "public");
		requestBody.put("visitedDate", localDate);
		requestBody.put("tags", new String[]{"맛집", "꿀맛"});
		requestBody.put("images", new String[]{"https://s3.ap-northeast-2.amazonaws.com/sikdorak/test.jpg"});

		given()
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.header("Content-type", "application/json")
				.body(requestBody)

				// 2022-02-01

				.when()
				.post("/api/reviews")

				.then()
				.statusCode(expectedStatusCode);

	}

	private static Stream<Arguments> provideContentForIsNullAndEmptyAnd500char() {
		String content = "a";
		return Stream.of(
			Arguments.of(null, HttpStatus.BAD_REQUEST.value()),
			Arguments.of("", HttpStatus.BAD_REQUEST.value()),
			Arguments.of(" ", HttpStatus.BAD_REQUEST.value()),
			Arguments.of("\t", HttpStatus.BAD_REQUEST.value()),
			Arguments.of("\n", HttpStatus.BAD_REQUEST.value()),
			Arguments.of(content.repeat(500), HttpStatus.BAD_REQUEST.value())
		);
	}

	private static Stream<Arguments> provideStoreIdForIsNullAndZeroAndMinusAndUnregistered() {
		long unregisteredStoreId = Long.MAX_VALUE;

		return Stream.of(
			Arguments.of(null, HttpStatus.BAD_REQUEST.value()),
			Arguments.of(0L, HttpStatus.BAD_REQUEST.value()),
			Arguments.of(-1L, HttpStatus.BAD_REQUEST.value()),
			Arguments.of(unregisteredStoreId, HttpStatus.BAD_REQUEST.value())
		);
	}
}
