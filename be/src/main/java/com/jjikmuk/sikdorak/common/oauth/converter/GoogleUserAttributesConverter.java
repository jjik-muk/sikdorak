package com.jjikmuk.sikdorak.common.oauth.converter;

import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthUserProfile;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GoogleUserAttributesConverter extends OAuthUserAttributesConverter{

    @Override
    public OAuthUserProfile convert(String userNameAttribute, Map<String, Object> attributes) {
        long uniqueId = 12345678L;
        String nickname = (String) attributes.get("name");
        String profileImage = (String) attributes.get("picture");
        String email = (String) attributes.get("email");

        return new OAuthUserProfile(uniqueId, nickname, profileImage, email);
    }
}
