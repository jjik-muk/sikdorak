package com.jjikmuk.sikdorak.common.oauth.converter;

import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthUserProfile;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GithubUserAttributesConverter extends OAuthUserAttributesConverter{

    @Override
    public OAuthUserProfile convert(String userNameAttribute, Map<String, Object> attributes) {
        String uniqueId = (String) attributes.get(userNameAttribute);
        String nickname = (String) attributes.get("name");
        String profileImage = (String) attributes.get("avatar_url");
        String email = (String) attributes.get("email");

        return new OAuthUserProfile(uniqueId, nickname, profileImage, email);
    }
}
