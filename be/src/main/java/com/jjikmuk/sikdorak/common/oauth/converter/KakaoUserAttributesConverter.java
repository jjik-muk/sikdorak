package com.jjikmuk.sikdorak.common.oauth.converter;

import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthUserProfile;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class KakaoUserAttributesConverter extends OAuthUserAttributesConverter {

    @Override
    public OAuthUserProfile convert(String userNameAttribute, Map<String, Object> attributes) {
        long uniqueId = Long.parseLong((String)attributes.get(userNameAttribute));
        String nickname = (String) attributes.get("nickname");
        String profileImage = (String) attributes.get("profile_image_url");
        boolean hasEmail = Boolean.parseBoolean((String)attributes.get("has_email"));

        if (hasEmail) {
            boolean isEmailValid = Boolean.parseBoolean((String)attributes.get("is_email_valid"));
            boolean isEmailVerified = Boolean.parseBoolean((String)attributes.get("is_email_verified"));

            if (isEmailValid && isEmailVerified) {
                String email = (String) attributes.get("email");
                return new OAuthUserProfile(uniqueId, nickname, profileImage, email);
            }
        }

        return new OAuthUserProfile(uniqueId, nickname, profileImage);
    }
}
