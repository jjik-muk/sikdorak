package com.jjikmuk.sikdorak.user.auth.app.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jjikmuk.sikdorak.common.config.feignclient.OAuthUserAccountResponseDeserializer;
import java.util.Map;
import lombok.Getter;

@JsonDeserialize(using = OAuthUserAccountResponseDeserializer.class)
@Getter
public class OAuthUserResponse {

    private final Map<String, Object> userInfo;

    private OAuthUserResponse(Map<String, Object> userInfo) {
        this.userInfo = userInfo;
    }

    public static OAuthUserResponse of(Map<String, Object> convertValue) {
        return new OAuthUserResponse(convertValue);
    }
}
