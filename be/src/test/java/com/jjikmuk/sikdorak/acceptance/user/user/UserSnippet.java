package com.jjikmuk.sikdorak.acceptance.user.user;

import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfCommon;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfCommonNonData;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfListWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.acceptance.DocumentFormatGenerator.responseFieldsOfObjectWithConstraintsAndFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.jjikmuk.sikdorak.user.user.controller.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.controller.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.controller.response.FollowUserProfile;
import com.jjikmuk.sikdorak.user.user.controller.response.UserDetailProfileResponse;
import com.jjikmuk.sikdorak.user.user.controller.response.UserReviewResponse;
import com.jjikmuk.sikdorak.user.user.controller.response.UserSimpleProfileResponse;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface UserSnippet {

    Snippet USER_MODIFY_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
        UserModifyRequest.class,
        fieldWithPath("nickname").type(JsonFieldType.STRING).description("nickname"),
        fieldWithPath("email").type(JsonFieldType.STRING).description("email"),
        fieldWithPath("profileImage").type(JsonFieldType.STRING).description("프로필 이미지")
    );

    Snippet USER_MODIFY_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

    Snippet USER_FOLLOW_REQUEST_SNIPPET = requestSnippetWithConstraintsAndFields(
        UserFollowAndUnfollowRequest.class,
        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("팔로우 할 유저 아이디")
    );

    Snippet USER_FOLLOW_RESPONSE_SNIPPET = createResponseSnippetWithFields(responseFieldsOfCommonNonData());

    Snippet USER_SEARCH_REVIEWS_REQUEST_PARAM_SNIPPET = pathParameters(
        parameterWithName("userId").description("리뷰를 조회할 유저 아이디")
    );

    Snippet USER_SEARCH_REVIEWS_RESPONSE_SNIPPET = createResponseSnippetWithFields(
        responseFieldsOfCommon(),

        responseFieldsOfListWithConstraintsAndFields(
            UserReviewResponse.class,
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
            fieldWithPath("userId").type(JsonFieldType.NUMBER).description("리뷰를 작성한 유저 아이디"),
            fieldWithPath("storeId").type(JsonFieldType.NUMBER).description("리뷰 작성 대상 가게 아이디"),
            fieldWithPath("reviewContent").type(JsonFieldType.STRING).description("리뷰 내용"),
            fieldWithPath("reviewScore").type(JsonFieldType.NUMBER).description("리뷰 평점"),
            fieldWithPath("reviewVisibility").type(JsonFieldType.STRING).description("리뷰 보기 권한"),
            fieldWithPath("visitedDate").type(JsonFieldType.STRING).description("가게 방문 날짜"),
            fieldWithPath("tags").type(JsonFieldType.ARRAY).description("리뷰 태그들"),
            fieldWithPath("images").type(JsonFieldType.ARRAY).description("리뷰 이미지"),
            fieldWithPath("createdAt").type(JsonFieldType.STRING).description("리뷰 생성 시간"),
            fieldWithPath("updatedAt").type(JsonFieldType.STRING).description("리뷰 수정 시간")
        )
    );

    Snippet USER_SEARCH_PROFILE_REQUEST_SNIPPET = pathParameters(
        parameterWithName("userId").description("프로필을 조회할 유저 아이디")
    );

    Snippet USER_SEARCH_PROFILE_RESPONSE_SNIPPET = createResponseSnippetWithFields(
        responseFieldsOfCommon(),

        responseFieldsOfObjectWithConstraintsAndFields(
            UserDetailProfileResponse.class,
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
            fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
            fieldWithPath("email").type(JsonFieldType.STRING).description("유저 이메일"),
            fieldWithPath("profileImage").type(JsonFieldType.STRING).description("유저 프로필 이미지"),
            fieldWithPath("relationStatus.isViewer").type(JsonFieldType.BOOLEAN).description("자신의 프로필 조회 여부"),
            fieldWithPath("relationStatus.followStatus").type(JsonFieldType.BOOLEAN).description("요청 유저와의 관계"),
            fieldWithPath("followersCount").type(JsonFieldType.NUMBER).description("유저 팔로워 수"),
            fieldWithPath("followingCount").type(JsonFieldType.NUMBER).description("유저 팔로잉 수"),
            fieldWithPath("reviewCount").type(JsonFieldType.NUMBER).description("유저 게시물 수")
        )
    );

    Snippet USER_DELETE_RESPONSE_SNIPPET = createResponseSnippetWithFields(
        responseFieldsOfCommonNonData());

    Snippet USER_SEARCH_SELF_PROFILE_RESPONSE_SNIPPET = createResponseSnippetWithFields(
        responseFieldsOfCommon(),

        responseFieldsOfObjectWithConstraintsAndFields(
            UserSimpleProfileResponse.class,
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
            fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
            fieldWithPath("profileImage").type(JsonFieldType.STRING).description("유저 프로필 이미지")
        )
    );

    Snippet USER_SEARCH_FOLLOWERS_FOLLOWINGS_REQUEST_SNIPPET = pathParameters(
        parameterWithName("userId").description("팔로워/팔로워 목록을 조회할 유저 아이디")
    );

    Snippet USER_SEARCH_FOLLOWERS_FOLLOWINGS_RESPONSE_SNIPPET = createResponseSnippetWithFields(
        responseFieldsOfCommon(),

        responseFieldsOfListWithConstraintsAndFields(
            FollowUserProfile.class,
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
            fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
            fieldWithPath("profileImage").type(JsonFieldType.STRING).description("유저 프로필 이미지"),
            fieldWithPath("isViewer").type(JsonFieldType.BOOLEAN).description("자신의 프로필 여부"),
            fieldWithPath("followStatus").type(JsonFieldType.BOOLEAN).description("유저와의 관계")
        )
    );

    Snippet USER_SEARCH_BY_NICKNAME_REQUEST_SNIPPET = pathParameters(
        parameterWithName("userId").description("팔로워/팔로워 목록을 조회할 유저 아이디")
    );

    Snippet USER_SEARCH_BY_NICKNAME_RESPONSE_SNIPPET = createResponseSnippetWithFields(
        responseFieldsOfCommon(),

        responseFieldsOfListWithConstraintsAndFields(
            FollowUserProfile.class,
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
            fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
            fieldWithPath("profileImage").type(JsonFieldType.STRING).description("유저 프로필 이미지"),
            fieldWithPath("isViewer").type(JsonFieldType.BOOLEAN).description("자신의 프로필 여부"),
            fieldWithPath("followStatus").type(JsonFieldType.BOOLEAN).description("유저와의 관계")
        )
    );


}
