package com.jjikmuk.sikdorak.acceptance;

import static io.restassured.RestAssured.given;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 *  요청이 정상적인 경우 정상 상태 코드를 반환한다
 *  요청의 필수 데이터가 빠진경우(텍스트, 식당, 방문일, 평점, 공개 범위) 실패 상태 코드를 반환한다.
 *  요청 텍스트가 유효하지 않은 경우(null, empty, 500자 넘는 경우)
 *  요청 식당 id가 유효하지 않은 경우(null, 0, -1, 등록되지 않은 값)
 *  요청 방문일이 유효하지 않은 경우(미래 날짜, 유효하지 않은 날짜 형식)
 *  요청 평점이 유효하지 않은 경우(1,2,3,4,5 가 아닌 경우)
 *  요청 태그들이 유효하지 않은 경우(공백 포함, 한글 영어 숫자 이외의 값, 50자 초과, 개수 30개 초과)
 *  요청 공개 범위가 유효하지 않은 경우(public, protected, private 이외의 값, null, empty)
 */
public class ReviewAccecptanceTest extends InitAcceptanceTest {

	@Test
	@DisplayName("리뷰 생성 요청이 정상적인 경우라면 리뷰 생성 후 정상 상태 코드를 반환한다")
	void create_review_success() {
		JSONObject requestBody = new JSONObject();
		requestBody.put("reviewContent", "찐맛집입니다.");
		requestBody.put("storeId", 1);
		requestBody.put("reviewScore", 4);
		requestBody.put("reviewVisibility", "public");
		requestBody.put("tags", new String[]{"맛집", "꿀맛"});

		given()
			.accept(MediaType.APPLICATION_JSON_VALUE)
			.header("Content-type", "application/json")
			.body(requestBody)

		.when()
			.post("/api/reviews")

		.then()
			.statusCode(HttpStatus.CREATED.value());
	}

}
