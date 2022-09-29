package com.jjikmuk.sikdorak.documentationtest.user.auth;

import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseSnippetOfCommonNonData;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseSnippetOfCommonWithConstraints;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import com.jjikmuk.sikdorak.user.auth.domain.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.service.response.AccessTokenResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface OAuthSnippet {

    Snippet LOGIN_SUCCESS_REQUEST_SNIPPET = requestParameters(
            parameterWithName("code").description("Kakao Authorization Code")
    );

    Snippet LOGIN_SUCCESS_RESPONSE_SNIPPET = responseSnippetOfCommonWithConstraints(
            JwtTokenPair.class,
            fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰")
    );

    Snippet UPDATE_ACCESS_TOKEN_REQUEST_SNIPPET = requestHeaders(
        headerWithName("Cookie").description("리프레쉬 토큰")
    );


    Snippet UPDATE_ACCESS_TOKEN_SUCCESS_RESPONSE_SNIPPET = responseSnippetOfCommonWithConstraints(
        AccessTokenResponse.class,
        fieldWithPath("accessToken").type(JsonFieldType.STRING).description("액세스 토큰")
    );

    Snippet UPDATE_ACCESS_TOKEN_FAIL_RESPONSE_SNIPPET = responseSnippetOfCommonNonData();
}
