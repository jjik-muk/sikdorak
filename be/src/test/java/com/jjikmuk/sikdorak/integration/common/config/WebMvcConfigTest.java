package com.jjikmuk.sikdorak.integration.common.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jjikmuk.sikdorak.user.auth.api.LoginUserArgumentResolver;
import com.jjikmuk.sikdorak.user.user.command.app.UserService;
import java.net.URI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@WebMvcTest(CorsTestController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ActiveProfiles("test")
@DisplayName("통합 : CORS 설정")
class WebMvcConfigTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private LoginUserArgumentResolver loginUserArgumentResolver; // application context 용도로 필요

    @MockBean
    private UserService userService; // application context 용도로 필요

    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE", "OPTIONS"})
    @DisplayName("CORS 설정에서 허용된 HTTP Method 로 오는 요청은 성공한다")
    void cors_allowed_method_ok(String method) {
        assertCors(method, status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"HEAD", "PATCH"})
    @DisplayName("CORS 설정에서 허용되지 않은 HTTP Method 로 오는 요청은 실패한다.")
    void cors_not_allowed_method_forbidden(String method) {
        assertCors(method, status().isForbidden());
    }

    @Test
    @DisplayName("CORS 설정과 관계없이 TRACE 메소드로 오는 요청은 항상 성공한다.")
    void always_allowed_method_ok() {
        assertCors(HttpMethod.TRACE.name(), status().isOk());
    }

    @Test
    @DisplayName("CORS 설정에서 허용된 Origin 에서 오는 요청은 성공한다.")
    void cors_allowed_origin_ok() {
        String allowedOrigin = "http://localhost:3000";
        assertCors(HttpMethod.GET.name(), allowedOrigin, status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {"https://google.com", "https://naver.com", "http://localhost:1234",
        "https://localhost:5678"})
    @DisplayName("CORS 설정에서 허용되지 않은 Origin 에서 오는 요청은 실패한다.")
    void cors_not_allowed_origin_forbidden(String origin) {
        assertCors(HttpMethod.GET.name(), origin, status().isForbidden());
    }

    private void assertCors(String method, ResultMatcher statusResult) {
        assertCors(method, "http://localhost:3000", statusResult);
    }

    private void assertCors(String method, String origin, ResultMatcher statusResult) {
        try {
            mvc.perform(request(method, URI.create(CorsTestController.REQUEST_URL))
                    .header("Origin", origin)
                    .header("Access-Control-Request-Method", method))
                .andExpect(statusResult);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
