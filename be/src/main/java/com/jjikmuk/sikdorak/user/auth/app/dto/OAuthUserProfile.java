package com.jjikmuk.sikdorak.user.auth.app.dto;

import com.jjikmuk.sikdorak.user.user.command.domain.User;
import lombok.Getter;

@Getter
public class OAuthUserProfile {
    private final String uniqueId;
    private final String nickname;
    private final String profileImage;
    private final String email;

    public OAuthUserProfile(String uniqueId, String nickname, String profileImage) {
        this(uniqueId, nickname, profileImage, null);
    }

    public OAuthUserProfile(String uniqueId, String nickname, String profileImage, String email) {
        this.uniqueId = uniqueId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;
    }

    public User toEntity() {
        return new User(uniqueId, nickname, profileImage, email);
    }
}
