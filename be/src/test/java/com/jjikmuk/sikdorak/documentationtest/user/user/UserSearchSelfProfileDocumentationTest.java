package com.jjikmuk.sikdorak.documentationtest.user.user;

import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_SEARCH_SELF_PROFILE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : User 자신의 프로필 조회")
class UserSearchSelfProfileDocumentationTest extends InitDocumentationTest {

    @Test
    @DisplayName("유저 본인의 정보 요청이 들어오면 성공 상태코드를 반환한다.")
    void search_self_profile_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_SEARCH_SELF_PROFILE_RESPONSE_SNIPPET))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Authorization", testData.user1ValidAuthorizationHeader)

        .when()
            .get("/api/users/me")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_SEARCH_PROFILE_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_SEARCH_PROFILE_SUCCESS.getMessage()))
            .body("data.id", equalTo(testData.kukim.getId().intValue()))
            .body("data.nickname", equalTo(testData.kukim.getNickname()))
            .body("data.profileImage", equalTo(testData.kukim.getProfileImage()));
    }

}
