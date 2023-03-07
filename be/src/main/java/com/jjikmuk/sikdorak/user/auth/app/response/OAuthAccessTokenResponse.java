package com.jjikmuk.sikdorak.user.auth.app.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class OAuthAccessTokenResponse {

    private String tokenType;
    private String accessToken;

}
