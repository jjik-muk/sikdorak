package com.jjikmuk.sikdorak.user.user.controller.request;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFollowAndUnfollowRequest {

    @NotNull
    private long userId;

    public UserFollowAndUnfollowRequest(long userId) {
        this.userId = userId;
    }
}
