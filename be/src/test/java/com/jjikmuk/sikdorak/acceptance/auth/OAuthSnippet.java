package com.jjikmuk.sikdorak.acceptance.auth;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonSingleResponseFieldsWithValidConstraints;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import com.jjikmuk.sikdorak.auth.controller.response.JwtTokenResponse;
import com.jjikmuk.sikdorak.auth.controller.response.SikdorakAccessToken;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface OAuthSnippet {

    Snippet LOGIN_SUCCESS_REQUEST_SNIPPET = requestParameters(
            parameterWithName("code").description("Kakao Authorization Code")
    );

    Snippet LOGIN_SUCCESS_RESPONSE_SNIPPET = commonSingleResponseFieldsWithValidConstraints(
            JwtTokenResponse.class,
            fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰"),
            fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("리프레쉬 토큰")
    );

    Snippet UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET = requestHeaders(
        headerWithName("Cookie").description("리프레쉬 토큰")
    );


    Snippet UPDATE_ACCESS_TOKEN_RESPONSE_SNIPPET = commonSingleResponseFieldsWithValidConstraints(
        SikdorakAccessToken.class,
        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰")
    );
}
