package com.jjikmuk.sikdorak.acceptance.user.user;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("유저 팔로워 목록 조회 인수 테스트")
public class UserFollowersSearchByUserIdAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("특정 유저에 대한 팔로워 조회 요청이 올바를 경우 성공 상태코드를 반환한다.")
    void search_user_followers_success() {
        given(this.spec)
//            .filter(document(
//                DEFAULT_RESTDOC_PATH,
//                USER_SEARCH_FOLLOWERS_REQUEST_SNIPPET,
//                USER_SEARCH_FOLLOWERS_RESPONSE_SNIPPET
//            ))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", testData.user1ValidAuthorizationHeader)

        .when()
            .get("/api/users/{userId}/followers", testData.rumka.getId())

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_SEARCH_FOLLOWERS_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_SEARCH_FOLLOWERS_SUCCESS.getMessage()))
            .body("data.size()", equalTo(2));
    }

}
