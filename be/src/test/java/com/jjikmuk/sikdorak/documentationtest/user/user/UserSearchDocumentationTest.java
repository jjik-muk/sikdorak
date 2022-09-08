package com.jjikmuk.sikdorak.documentationtest.user.user;

import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_SEARCH_BY_NICKNAME_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_SEARCH_BY_NICKNAME_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("유저 검색 문서테스트")
class UserSearchDocumentationTest extends InitDocumentationTest {

    @Test
    @DisplayName("유저 검색 요청이 올바를 경우 유저 목록과 성공 상태코드를 반환한다.")
    void search_user_by_nickname_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_SEARCH_BY_NICKNAME_REQUEST_SNIPPET,
                USER_SEARCH_BY_NICKNAME_RESPONSE_SNIPPET
            ))
            .queryParam("nickname", "쿠")

            .when()
            .get("/api/users")

            .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_SEARCH_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_SEARCH_SUCCESS.getMessage()))
            .body("data", hasSize(1));
    }
}
