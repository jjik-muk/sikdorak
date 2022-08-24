package com.jjikmuk.sikdorak.user.user.controller;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import com.jjikmuk.sikdorak.user.user.controller.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.controller.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.controller.response.UserProfileResponse;
import com.jjikmuk.sikdorak.user.user.controller.response.UserReviewResponse;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/reviews")
    public CommonResponseEntity<List<UserReviewResponse>> searchReviewsByUserId(
        @PathVariable Long userId,
        @AuthenticatedUser LoginUser loginUser) {

        List<UserReviewResponse> userReviewResponses = userService.searchUserReviewsByUserIdAndRelationType(userId, loginUser);

        return new CommonResponseEntity<>(
            ResponseCodeAndMessages.USER_SEARCH_REVIEWS_SUCCESS,
            userReviewResponses,
            HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public CommonResponseEntity<UserProfileResponse> searchUserProfileByUserID(@AuthenticatedUser LoginUser loginUser,
        @PathVariable Long userId
    ) {

        UserProfileResponse userProfileResponse = userService.searchUserProfile(userId, loginUser);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_SEARCH_PROFILE_SUCCESS,
            userProfileResponse,
            HttpStatus.OK);
    }

    @UserOnly
    @PutMapping("")
    public CommonResponseEntity<Void> modifyUserProfile(@AuthenticatedUser LoginUser loginUser,
        @RequestBody UserModifyRequest userModifyRequest) {

        userService.modifyUser(loginUser, userModifyRequest);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_MODIFY_SUCCESS,
            HttpStatus.OK);

    }

    @UserOnly
    @PutMapping("/follow")
    public CommonResponseEntity<Void> follow(@AuthenticatedUser LoginUser loginUser,
        @RequestBody UserFollowAndUnfollowRequest userFollowAndUnfollowRequest) {

        userService.followUser(loginUser, userFollowAndUnfollowRequest);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_FOLLOW_SUCCESS,
            HttpStatus.OK);
    }

    @UserOnly
    @PutMapping("/unfollow")
    public CommonResponseEntity<Void> unfollow(@AuthenticatedUser LoginUser loginUser,
        @RequestBody UserFollowAndUnfollowRequest userFollowAndUnfollowRequest) {

        userService.unfollowUser(loginUser, userFollowAndUnfollowRequest);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_UNFOLLOW_SUCCESS,
            HttpStatus.OK);
    }
}
