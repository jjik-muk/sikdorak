package com.jjikmuk.sikdorak.auth.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UserProfileResponse {

    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;

}
