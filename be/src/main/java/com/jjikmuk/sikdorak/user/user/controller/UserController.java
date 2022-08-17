package com.jjikmuk.sikdorak.user.user.controller;

import com.jjikmuk.sikdorak.common.ResponseCodeAndMessages;
import com.jjikmuk.sikdorak.common.aop.UserOnly;
import com.jjikmuk.sikdorak.common.response.CommonResponseEntity;
import com.jjikmuk.sikdorak.user.auth.controller.LoginUser;
import com.jjikmuk.sikdorak.user.auth.domain.AuthenticatedUser;
import com.jjikmuk.sikdorak.user.user.controller.request.UserModifyRequest;
import com.jjikmuk.sikdorak.user.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @UserOnly
    @PutMapping("/api/user")
    public CommonResponseEntity<Void> modifyUserProfile(@AuthenticatedUser LoginUser loginUser,
        @RequestBody UserModifyRequest userModifyRequest) {

        userService.modifyUser(loginUser, userModifyRequest);

        return new CommonResponseEntity<>(ResponseCodeAndMessages.USER_MODIFY_SUCCESS,
            HttpStatus.OK);

    }
}
