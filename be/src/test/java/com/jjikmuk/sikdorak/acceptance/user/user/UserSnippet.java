package com.jjikmuk.sikdorak.acceptance.user.user;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonRequestFieldsWithValidConstraints;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.commonResponseNonFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import com.jjikmuk.sikdorak.user.user.controller.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.controller.request.UserModifyRequest;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface UserSnippet {

    Snippet USER_MODIFY_REQUEST_SNIPPET = commonRequestFieldsWithValidConstraints(
        UserModifyRequest.class,
        fieldWithPath("nickname").type(JsonFieldType.STRING).description("nickname"),
        fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
    );

    Snippet USER_MODIFY_RESPONSE_SNIPPET = commonResponseNonFields();

    Snippet USER_FOLLOW_REQUEST_SNIPPET = commonRequestFieldsWithValidConstraints(
        UserFollowAndUnfollowRequest.class,
        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("팔로우 할 유저 아이디")
    );

    Snippet USER_FOLLOW_RESPONSE_SNIPPET = commonResponseNonFields();

}
