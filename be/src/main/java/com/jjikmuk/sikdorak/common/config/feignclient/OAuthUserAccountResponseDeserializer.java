package com.jjikmuk.sikdorak.common.config.feignclient;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jjikmuk.sikdorak.user.auth.app.response.OAuthUserResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OAuthUserAccountResponseDeserializer extends JsonDeserializer<OAuthUserResponse> {

    @Override
    public OAuthUserResponse deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JacksonException {
        JsonNode jsonNode = p.getCodec().readTree(p);

        Map<String, Object> attributes = new HashMap<>();
        flatResponse(attributes, jsonNode);

        return OAuthUserResponse.of(attributes);
    }

    private void flatResponse(Map<String, Object> attributes, JsonNode jsonNode) {
        Iterator<String> iterator = jsonNode.fieldNames();

        while (iterator.hasNext()) {
            String key = iterator.next();
            JsonNode curNode = jsonNode.get(key);
            if (curNode.isContainerNode()) {
                flatResponse(attributes, curNode);
                continue;
            }
            attributes.put(key, curNode.asText());
        }
    }

}
