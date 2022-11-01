package com.jjikmuk.sikdorak.documentationtest.user.user;

import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.DEFAULT_PAGING_AFTER_REQUEST_PARAMETER;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.DEFAULT_PAGING_BEFORE_REQUEST_PARAMETER;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.DEFAULT_PAGING_SIZE_REQUEST_PARAMETER;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.createResponseSnippetWithFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.requestPagingFieldsOfCommon;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.requestSnippetWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfCommon;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfCommonNonData;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfListWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responseFieldsOfObjectWithConstraintsAndFields;
import static com.jjikmuk.sikdorak.documentationtest.DocumentFormatGenerator.responsePagingFieldsOfCommon;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailLikeResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailStoreResponse;
import com.jjikmuk.sikdorak.review.query.response.reviewdetail.ReviewDetailUserResponse;
import com.jjikmuk.sikdorak.user.user.command.app.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.command.app.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.query.response.FollowUserProfile;
import com.jjikmuk.sikdorak.user.user.query.response.UserDetailProfileResponse;
import com.jjikmuk.sikdorak.user.user.query.response.UserSimpleProfileResponse;
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

    Snippet USER_SEARCH_REVIEWS_REQUEST_PATH_PARAM_SNIPPET = pathParameters(
        parameterWithName("userId").description("리뷰를 조회할 유저 아이디")
    );

    Snippet USER_SEARCH_REVIEWS_REQUEST_QUERY_PARAM_SNIPPET = requestPagingFieldsOfCommon();

    Snippet USER_SEARCH_REVIEWS_RESPONSE_SNIPPET = createResponseSnippetWithFields(
        responseFieldsOfCommon(),

        responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailUserResponse.class,
            fieldWithPath("reviews[].user").type(JsonFieldType.OBJECT).description("유저 정보"),
            fieldWithPath("reviews[].user.userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
            fieldWithPath("reviews[].user.userNickname").type(JsonFieldType.STRING).description("유저 이름"),
            fieldWithPath("reviews[].user.userProfileImage").type(JsonFieldType.STRING)
                .description("유저 프로필 이미지")),

        responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailStoreResponse.class,
            fieldWithPath("reviews[].store").type(JsonFieldType.OBJECT).description("가게 정보"),
            fieldWithPath("reviews[].store.storeId").type(JsonFieldType.NUMBER).description("가게 아이디"),
            fieldWithPath("reviews[].store.storeName").type(JsonFieldType.STRING).description("가게 이름"),
            fieldWithPath("reviews[].store.addressName").type(JsonFieldType.STRING).description("지번 주소"),
            fieldWithPath("reviews[].store.roadAddressName").type(JsonFieldType.STRING).description("도로명 주소")),

        responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailLikeResponse.class,
            fieldWithPath("reviews[].like.count").type(JsonFieldType.NUMBER).description("좋아요 개수"),
            fieldWithPath("reviews[].like.userLikeStatus").type(JsonFieldType.BOOLEAN).description("유저의 좋아요 여부")),

        responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailResponse.class,
            fieldWithPath("reviews[].reviewId").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
            fieldWithPath("reviews[].reviewContent").type(JsonFieldType.STRING).description("리뷰 내용"),
            fieldWithPath("reviews[].reviewScore").type(JsonFieldType.NUMBER).description("리뷰 점수"),
            fieldWithPath("reviews[].reviewVisibility").type(JsonFieldType.STRING).description("리뷰 게시물의 공개 범위"),
            fieldWithPath("reviews[].visitedDate").type(JsonFieldType.STRING).description("가게 방문일"),
            fieldWithPath("reviews[].tags").type(JsonFieldType.ARRAY).description("리뷰를 표현하는 태그들"),
            fieldWithPath("reviews[].images").type(JsonFieldType.ARRAY).description("리뷰를 위한 사진 URL"),
            fieldWithPath("reviews[].createdAt").type(JsonFieldType.STRING).description("리뷰 생성 시간"),
            fieldWithPath("reviews[].updatedAt").type(JsonFieldType.STRING).description("리뷰 수정 시간")),

        responsePagingFieldsOfCommon()
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

    Snippet USER_SEARCH_BY_NICKNAME_REQUEST_SNIPPET = requestParameters(
        parameterWithName("nickname").description("검색할 유저의 닉네임")
    );

    Snippet USER_SEARCH_BY_NICKNAME_RESPONSE_SNIPPET = createResponseSnippetWithFields(
        responseFieldsOfCommon(),

        responseFieldsOfListWithConstraintsAndFields(
            UserSimpleProfileResponse.class,
            fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 아이디"),
            fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
            fieldWithPath("profileImage").type(JsonFieldType.STRING).description("유저 프로필 이미지")
        )
    );

    Snippet USER_REVIEW_SEARCH_BY_RADIUS_REQUEST = requestParameters(
        parameterWithName("type").description("요청하는 페이지 타입 - feed | maps 로 나뉩니다."),
        parameterWithName("x").description("경도(최대값: 180.0 / 최소값: -180.0)"),
        parameterWithName("y").description("위도(최대값: 90.0 / 최소값: -90.0)"),
        parameterWithName("radius").description("위치 반경(최대값: 100 / 최소값: 20000)"),
        DEFAULT_PAGING_SIZE_REQUEST_PARAMETER,
        DEFAULT_PAGING_BEFORE_REQUEST_PARAMETER,
        DEFAULT_PAGING_AFTER_REQUEST_PARAMETER
    );

    Snippet USER_REVIEW_SEARCH_BY_RADIUS_RESPONSE = createResponseSnippetWithFields(
        responseFieldsOfCommon(),

        responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailUserResponse.class,
            fieldWithPath("reviews[].user").type(JsonFieldType.OBJECT).description("유저 정보"),
            fieldWithPath("reviews[].user.userId").type(JsonFieldType.NUMBER).description("유저 아이디"),
            fieldWithPath("reviews[].user.userNickname").type(JsonFieldType.STRING).description("유저 이름"),
            fieldWithPath("reviews[].user.userProfileImage").type(JsonFieldType.STRING)
                .description("유저 프로필 이미지")),

        responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailStoreResponse.class,
            fieldWithPath("reviews[].store").type(JsonFieldType.OBJECT).description("가게 정보"),
            fieldWithPath("reviews[].store.storeId").type(JsonFieldType.NUMBER).description("가게 아이디"),
            fieldWithPath("reviews[].store.storeName").type(JsonFieldType.STRING).description("가게 이름"),
            fieldWithPath("reviews[].store.addressName").type(JsonFieldType.STRING).description("지번 주소"),
            fieldWithPath("reviews[].store.roadAddressName").type(JsonFieldType.STRING).description("도로명 주소"),
            fieldWithPath("reviews[].store.x").type(JsonFieldType.NUMBER).description("경도(최대값: 180.0 / 최소값: -180.0)"),
            fieldWithPath("reviews[].store.y").type(JsonFieldType.NUMBER).description("위도(최대값: 90.0 / 최소값: -90.0)")),

        responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailLikeResponse.class,
            fieldWithPath("reviews[].like.count").type(JsonFieldType.NUMBER).description("좋아요 개수"),
            fieldWithPath("reviews[].like.userLikeStatus").type(JsonFieldType.BOOLEAN).description("유저의 좋아요 여부")),

        responseFieldsOfObjectWithConstraintsAndFields(ReviewDetailResponse.class,
            fieldWithPath("reviews[].reviewId").type(JsonFieldType.NUMBER).description("리뷰 아이디"),
            fieldWithPath("reviews[].reviewContent").type(JsonFieldType.STRING).description("리뷰 내용"),
            fieldWithPath("reviews[].reviewScore").type(JsonFieldType.NUMBER).description("리뷰 점수"),
            fieldWithPath("reviews[].reviewVisibility").type(JsonFieldType.STRING).description("리뷰 게시물의 공개 범위"),
            fieldWithPath("reviews[].visitedDate").type(JsonFieldType.STRING).description("가게 방문일"),
            fieldWithPath("reviews[].tags").type(JsonFieldType.ARRAY).description("리뷰를 표현하는 태그들"),
            fieldWithPath("reviews[].images").type(JsonFieldType.ARRAY).description("리뷰를 위한 사진 URL"),
            fieldWithPath("reviews[].createdAt").type(JsonFieldType.STRING).description("리뷰 생성 시간"),
            fieldWithPath("reviews[].updatedAt").type(JsonFieldType.STRING).description("리뷰 수정 시간")),

        responsePagingFieldsOfCommon()
    );


}
