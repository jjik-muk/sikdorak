package com.jjikmuk.sikdorak.user.auth.app.domain;

import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthUserProfile;
import java.util.Map;
import java.util.function.BiFunction;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OAuthUserAttributes {

    GOOGLE((userNameAttribute, userInfo) -> {
        long uniqueId = 12345678L;
        String nickname = (String) userInfo.get("name");
        String profileImage = (String) userInfo.get("picture");
        String email = (String) userInfo.get("email");

        return new OAuthUserProfile(uniqueId, nickname, profileImage, email);
    }),

    GITHUB((userNameAttribute, userInfo) -> {
        long uniqueId = 12345678L;
        String nickname = (String) userInfo.get("name");
        String profileImage = (String) userInfo.get("avatar_url");
        String email = (String) userInfo.get("email");

        return new OAuthUserProfile(uniqueId, nickname, profileImage, email);
    }),

    KAKAO((userNameAttribute, userInfo) -> {
        long uniqueId = Long.parseLong((String)userInfo.get(userNameAttribute));
        String nickname = (String) userInfo.get("nickname");
        String profileImage = (String) userInfo.get("profile_image_url");
        boolean hasEmail = Boolean.parseBoolean((String)userInfo.get("has_email"));

        if (hasEmail) {
            boolean isEmailValid = Boolean.parseBoolean((String)userInfo.get("is_email_valid"));
            boolean isEmailVerified = Boolean.parseBoolean((String)userInfo.get("is_email_verified"));

            if (isEmailValid && isEmailVerified) {
                String email = (String) userInfo.get("email");
                return new OAuthUserProfile(uniqueId, nickname, profileImage, email);
            }
        }

        return new OAuthUserProfile(uniqueId, nickname, profileImage);
    });

    private final BiFunction<String, Map<String, Object>, OAuthUserProfile> convertFunction;

    public static OAuthUserProfile convertToOAuthUserProfile(OAuthClientRegistration registration, Map<String, Object> userInfo) {
        return OAuthUserAttributes.valueOf(registration.getRegistrationId().toUpperCase())
            .convertFunction.apply(registration.getUserNameAttribute(),userInfo);
    }
}
