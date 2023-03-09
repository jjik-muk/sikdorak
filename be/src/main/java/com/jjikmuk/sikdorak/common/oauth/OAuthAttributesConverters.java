package com.jjikmuk.sikdorak.common.oauth;

import com.jjikmuk.sikdorak.common.oauth.converter.OAuthUserAttributesConverter;
import com.jjikmuk.sikdorak.user.auth.exception.NotFoundAttributeConverterException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class OAuthAttributesConverters {

    private final List<OAuthUserAttributesConverter> oAuthUserAttributesConverterList;
    private final Map<String, OAuthUserAttributesConverter> oAuthAttributesConverterMap;

    public OAuthAttributesConverters(
        List<OAuthUserAttributesConverter> oAuthUserAttributesConverterList) {
        this.oAuthUserAttributesConverterList = oAuthUserAttributesConverterList;
        this.oAuthAttributesConverterMap = getConverterMap();
    }

    public OAuthUserAttributesConverter findByConverterId(String registrationId) {
        OAuthUserAttributesConverter oAuthUserAttributesConverter = oAuthAttributesConverterMap.get(
            registrationId);
        if (Objects.isNull(oAuthUserAttributesConverter)) {
            throw new NotFoundAttributeConverterException();
        }
        return oAuthUserAttributesConverter;
    }

    private Map<String, OAuthUserAttributesConverter> getConverterMap() {
        return oAuthUserAttributesConverterList.stream()
            .collect(Collectors.toMap(OAuthUserAttributesConverter::getConverterId,
                converter -> converter));
    }

}
