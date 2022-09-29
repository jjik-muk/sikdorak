package com.jjikmuk.sikdorak.user.user.service.response;

import com.jjikmuk.sikdorak.user.user.domain.User;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

public record UserSimpleProfileResponse (

    @NotNull
    long id,

    @NotNull
    @Length(min = 1, max = 30)
    String nickname,

    @NotNull
    @URL
    String profileImage
) {

    public static UserSimpleProfileResponse from(User user) {
        return new UserSimpleProfileResponse(
            user.getId(),
            user.getNickname(),
            user.getProfileImage()
        );
    }
}
