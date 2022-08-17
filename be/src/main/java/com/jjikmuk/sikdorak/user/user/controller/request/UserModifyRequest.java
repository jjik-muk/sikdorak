package com.jjikmuk.sikdorak.user.user.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Getter
@NoArgsConstructor
public class UserModifyRequest {

    @NotNull
    @Length(min = 1, max = 30)
    private String nickname;

    @NotNull
    @Email
    private String email;

    @NotNull
    @URL
    private String profileImage;

    public UserModifyRequest(String nickname, String email, String profileImage) {
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
    }
}
