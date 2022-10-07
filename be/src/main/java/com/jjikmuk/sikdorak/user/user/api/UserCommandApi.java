package com.jjikmuk.sikdorak.user.user.api;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.api.LoginUser;
import com.jjikmuk.sikdorak.user.auth.api.AuthenticatedUser;
import com.jjikmuk.sikdorak.user.user.command.app.UserService;
import com.jjikmuk.sikdorak.user.user.command.app.request.UserFollowAndUnfollowRequest;
import com.jjikmuk.sikdorak.user.user.command.app.request.UserModifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserCommandApi {

    private final UserService userService;

    @UserOnly
    @PutMapping
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

    @UserOnly
    @DeleteMapping
    public CommonResponseEntity<Void> deleteUser(@AuthenticatedUser LoginUser loginUser) {

        userService.deleteUser(loginUser);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_DELETE_SUCCESS,
            HttpStatus.OK);
    }

}
