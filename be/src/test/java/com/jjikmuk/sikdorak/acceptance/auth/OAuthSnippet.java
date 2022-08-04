package com.jjikmuk.sikdorak.acceptance.auth;

import com.jjikmuk.sikdorak.auth.controller.response.JwtTokenResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonResponseFieldsWithValidConstraints;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

public interface OAuthSnippet {

    Snippet LOGIN_SUCCESS_REQUEST_SNIPPET = requestParameters(
            parameterWithName("code").description("Kakao Authorization Code")
    );

    Snippet LOGIN_SUCCESS_RESPONSE_SNIPPET = commonResponseFieldsWithValidConstraints(
            JwtTokenResponse.class,
            fieldWithPath("accessToken")
                    .type(JsonFieldType.STRING)
                    .description("액세스 토큰"),
            fieldWithPath("refreshToken")
                    .type(JsonFieldType.STRING)
                    .description("리프레쉬 토큰")
    );
}
