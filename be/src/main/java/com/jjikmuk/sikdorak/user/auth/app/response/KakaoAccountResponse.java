package com.jjikmuk.sikdorak.user.auth.app.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jjikmuk.sikdorak.common.config.feignclient.KakaoAccountResponseDeserializer;
import lombok.Getter;

@JsonDeserialize(using = KakaoAccountResponseDeserializer.class)
@Getter
public class KakaoAccountResponse {

    private long uniqueId;
    private String nickname;
    private String profileImage;
    private String email;

    public KakaoAccountResponse(long uniqueId, String nickname, String profileImage, String email) {
        this.uniqueId = uniqueId;
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;
    }
}
