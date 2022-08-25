package com.jjikmuk.sikdorak.common.config.feignclient;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jjikmuk.sikdorak.user.auth.controller.response.KakaoAccountResponse;
import java.io.IOException;

public class KakaoAccountResponseDeserializer extends JsonDeserializer<KakaoAccountResponse> {

    @Override
    public KakaoAccountResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException{

        JsonNode jsonNode = p.getCodec().readTree(p);

        long uniqueId = jsonNode.get("id").asLong();
        JsonNode kakaoAccountNode = jsonNode.get("kakao_account");

        String nickname = kakaoAccountNode.get("profile")
                .get("nickname")
                .asText();
        String profileImage = kakaoAccountNode.get("profile")
                .get("profile_image_url")
                .asText();

        if (kakaoAccountNode.has("email")) {
            String email = kakaoAccountNode.get("email").asText();
            boolean isEmailValid = kakaoAccountNode.get("is_email_valid").asBoolean();
            boolean isEmailVerified = kakaoAccountNode.get("is_email_verified").asBoolean();

            if (isEmailValid && isEmailVerified) {
                return new KakaoAccountResponse(uniqueId, nickname, profileImage, email);
            }
        }

        return new KakaoAccountResponse(uniqueId, nickname, profileImage, null);
    }
}
