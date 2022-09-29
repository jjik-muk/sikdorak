package com.jjikmuk.sikdorak.documentationtest.user.user;

import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_MODIFY_REQUEST_SNIPPET;
import static com.jjikmuk.sikdorak.documentationtest.user.user.UserSnippet.USER_MODIFY_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.exception.ExceptionCodeAndMessages;
import com.jjikmuk.sikdorak.documentationtest.InitDocumentationTest;
import com.jjikmuk.sikdorak.user.user.service.request.UserModifyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@DisplayName("문서화 : User 수정")
class UserModifyDocumentationTest extends InitDocumentationTest {

    @Test
    @DisplayName("유저 프로필 업데이트 요청이 정상적인 경우라면 프로필을 업데이트하고 정상 상태코드를 반환한다.")
    void modify_user_profile_success() {

        UserModifyRequest userModifyRequest = new UserModifyRequest(
            "포키",
            "forkyy@gmail.com",
            "https://s3.amazon-test.com/test.jpg"
        );

        given(this.spec)
            .filter(document(
                    DEFAULT_RESTDOC_PATH,
                    USER_MODIFY_REQUEST_SNIPPET,
                    USER_MODIFY_RESPONSE_SNIPPET))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", "application/json")
            .header("Authorization", testData.user1ValidAuthorizationHeader)
            .body(userModifyRequest)

        .when()
            .put("/api/users")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_MODIFY_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_MODIFY_SUCCESS.getMessage()));

    }

    @Test
    @DisplayName("비회원이 유저 프로필 업데이트 요청을 했을 경우라면 예외 상태코드를 반환한다. ")
    void anonymous_user_modify_request() {

        UserModifyRequest userModifyRequest = new UserModifyRequest(
            "포키",
            "forkyy@gmail.com",
            "https://s3.amazon-test/test.jpg"
        );

        given(this.spec)
            .filter(
                document(DEFAULT_RESTDOC_PATH,
                    USER_MODIFY_REQUEST_SNIPPET,
                    USER_MODIFY_RESPONSE_SNIPPET))
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("Content-type", "application/json")
            .body(userModifyRequest)

        .when()
            .put("/api/users")

        .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .body("code", equalTo(ExceptionCodeAndMessages.NEED_LOGIN.getCode()))
            .body("message", equalTo(ExceptionCodeAndMessages.NEED_LOGIN.getMessage()));
    }
}
