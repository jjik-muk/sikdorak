package com.jjikmuk.sikdorak.user.user.api;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.controller.CursorPageable;
import com.jjikmuk.sikdorak.common.controller.request.CursorPageRequest;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.review.query.ReviewDao;
import com.jjikmuk.sikdorak.review.query.response.ReviewListResponse;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.api.AuthenticatedUser;
import com.jjikmuk.sikdorak.user.user.query.response.FollowUserProfile;
import com.jjikmuk.sikdorak.user.user.query.response.UserDetailProfileResponse;
import com.jjikmuk.sikdorak.user.user.query.response.UserSimpleProfileResponse;
import com.jjikmuk.sikdorak.user.user.query.UserDao;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserQueryApi {

    private final UserDao userDao;
    private final ReviewDao reviewDao;

    @GetMapping
    public CommonResponseEntity<List<UserSimpleProfileResponse>> searchUsers(
        @RequestParam String nickname) {

        List<UserSimpleProfileResponse> userList = userDao.searchUsersByNickname(nickname);

        return new CommonResponseEntity<>(
            ResponseCodeAndMessages.USER_SEARCH_SUCCESS,
            userList,
            HttpStatus.OK
        );
    }

    @GetMapping("/{userId}/reviews")
    public CommonResponseEntity<ReviewListResponse> searchReviewsByUserId(
        @PathVariable Long userId,
        @AuthenticatedUser LoginUser loginUser,
        @CursorPageable CursorPageRequest cursorPageRequest) {

        ReviewListResponse userReviewResponses =
            reviewDao.searchUserReviewsByUserIdAndRelationType(userId, loginUser,
                cursorPageRequest);

        return new CommonResponseEntity<>(
            ResponseCodeAndMessages.USER_SEARCH_REVIEWS_SUCCESS,
            userReviewResponses,
            HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public CommonResponseEntity<UserDetailProfileResponse> searchUserProfileByUserID(
        @AuthenticatedUser LoginUser loginUser,
        @PathVariable Long userId
    ) {

        UserDetailProfileResponse userDetailProfileResponse = userDao.searchUserDetailProfile(
            userId, loginUser);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_SEARCH_PROFILE_SUCCESS,
            userDetailProfileResponse,
            HttpStatus.OK);
    }

    @UserOnly
    @GetMapping("/me")
    public CommonResponseEntity<UserSimpleProfileResponse> searchSelfProfile(
        @AuthenticatedUser LoginUser loginUser) {

        UserSimpleProfileResponse userSimpleProfileResponse = userDao.searchSelfProfile(
            loginUser);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_SEARCH_PROFILE_SUCCESS,
            userSimpleProfileResponse,
            HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers")
    public CommonResponseEntity<List<FollowUserProfile>> searchUserFollowers(
        @AuthenticatedUser LoginUser loginUser,
        @PathVariable Long userId) {

        List<FollowUserProfile> followersResponse = userDao.searchFollowersByUserId(
            userId, loginUser);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_SEARCH_FOLLOWERS_SUCCESS,
            followersResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followings")
    public CommonResponseEntity<List<FollowUserProfile>> searchUserFollowings(
        @AuthenticatedUser LoginUser loginUser,
        @PathVariable Long userId) {

        List<FollowUserProfile> followingsResponse = userDao.searchFollowingsByUserId(
            userId, loginUser);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_SEARCH_FOLLOWINGS_SUCCESS,
            followingsResponse, HttpStatus.OK);
    }

}
