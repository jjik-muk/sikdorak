package com.jjikmuk.sikdorak.auth.controller.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SikdorakAccessToken {

    private String accessToken;

    public SikdorakAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
