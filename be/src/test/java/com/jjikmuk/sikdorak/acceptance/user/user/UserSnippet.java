package com.jjikmuk.sikdorak.acceptance.user.user;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonResponseNonFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface UserSnippet {

    Snippet USER_MODIFY_PROFILE_PATH_VARIABLE_SNIPPET = pathParameters(
        parameterWithName("userId").description("유저 id")
    );

    Snippet USER_MODIFY_PROFILE_REQUEST_SNIPPET = requestFields(
        fieldWithPath("nickname").type(JsonFieldType.STRING).description("닉네임"),
        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
    );

    Snippet USER_MODIFY_PROFILE_RESPONSE_SNIPPET = commonResponseNonFields();

}
