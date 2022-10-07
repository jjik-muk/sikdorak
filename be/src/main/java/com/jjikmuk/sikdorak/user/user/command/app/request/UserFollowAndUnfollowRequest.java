package com.jjikmuk.sikdorak.user.user.command.app.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserFollowAndUnfollowRequest {

    @NotNull
    @Min(1)
    @Max(Long.MAX_VALUE)
    private long userId;


    public UserFollowAndUnfollowRequest(long userId) {
        this.userId = userId;
    }
}
