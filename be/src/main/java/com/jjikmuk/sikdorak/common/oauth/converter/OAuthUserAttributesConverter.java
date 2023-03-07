package com.jjikmuk.sikdorak.common.oauth.converter;

import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthUserProfile;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class OAuthUserAttributesConverter{

    private static final String DEFAULT_NAME = "UserAttributesConverter";
    protected String converterId = setConverterId();

    public abstract OAuthUserProfile convert(String userNameAttribute, Map<String, Object> attributes);

    protected String setConverterId(){
        return this.getClass().getSimpleName().replace(DEFAULT_NAME, "").toLowerCase();
    }
}
