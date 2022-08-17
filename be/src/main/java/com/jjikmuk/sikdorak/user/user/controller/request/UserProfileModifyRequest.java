package com.jjikmuk.sikdorak.user.user.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Getter
@RequiredArgsConstructor
public class UserProfileModifyRequest {

    @NotNull
    @Length(min = 1, max = 30)
    private final String nickname;

    @NotNull
    @Email
    private final String email;

    @NotNull
    @URL
    private final String profileImage;


}
