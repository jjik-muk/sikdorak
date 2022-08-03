package com.jjikmuk.sikdorak.common.config.feignclient;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jjikmuk.sikdorak.auth.controller.dto.response.KakaoAccountResponse;

import java.io.IOException;

public class KakaoAccountResponseDeserializer extends JsonDeserializer<KakaoAccountResponse> {

    @Override
    public KakaoAccountResponse deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {

        JsonNode jsonNode = p.getCodec().readTree(p);

        long uniqueId = jsonNode.get("id").asLong();
        JsonNode kakao_account_node = jsonNode.get("kakao_account");
        String nickname = kakao_account_node
                .get("profile")
                .get("nickname")
                .asText();
        String profileImage = kakao_account_node
                .get("profile")
                .get("profile_image_url")
                .asText();

        String email = kakao_account_node.get("email").asText();
        boolean isEmailValid = kakao_account_node.get("is_email_valid").asBoolean();
        boolean isEmailVerified = kakao_account_node.get("is_email_verified").asBoolean();


        if (isEmailValid && isEmailVerified) {
            return new KakaoAccountResponse(uniqueId, nickname, profileImage, email);
        }

        return new KakaoAccountResponse(uniqueId, nickname, profileImage, null);
    }
}
