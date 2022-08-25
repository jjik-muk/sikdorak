package com.jjikmuk.sikdorak.acceptance.user.user;

import static com.jjikmuk.sikdorak.acceptance.user.user.UserSnippet.USER_DELETE_RESPONSE_SNIPPET;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.document;

import com.jjikmuk.sikdorak.acceptance.InitAcceptanceTest;
import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@DisplayName("유저 탈퇴 인수 테스트")
public class UserDeleteAcceptanceTest extends InitAcceptanceTest {

    @Test
    @DisplayName("유저의 탈퇴 요청이 올바르면 성공 응답 코드를 반환한다.")
    void delete_user_success() {
        given(this.spec)
            .filter(document(
                DEFAULT_RESTDOC_PATH,
                USER_DELETE_RESPONSE_SNIPPET
            ))
            .header("Authorization", testData.user1ValidAuthorizationHeader)

        .when()
            .delete("/api/users")

        .then()
            .statusCode(HttpStatus.OK.value())
            .body("code", equalTo(ResponseCodeAndMessages.USER_DELETE_SUCCESS.getCode()))
            .body("message", equalTo(ResponseCodeAndMessages.USER_DELETE_SUCCESS.getMessage()));
    }

}


