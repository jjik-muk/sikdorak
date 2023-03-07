package com.jjikmuk.sikdorak.common.oauth;

import com.jjikmuk.sikdorak.common.properties.oauth.OAuthRegistrationProperties;
import com.jjikmuk.sikdorak.user.auth.exception.NotFoundClientRegistrationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InMemoryClientRegistrationRepository implements ClientRegistrationRepository {

    private final Map<String, OAuthClientRegistration> registrations;

    public InMemoryClientRegistrationRepository(
        OAuthRegistrationProperties oAuthRegistrationProperties,
        OAuthAttributesConverters converters) {
        this.registrations = createRegistrationMap(oAuthRegistrationProperties, converters);
    }

    @Override
    public OAuthClientRegistration findRegistrationByName(String registrationId) {
        if (Objects.isNull(registrations.get(registrationId.toLowerCase()))) {
            throw new NotFoundClientRegistrationException();
        }
        return registrations.get(registrationId.toLowerCase());
    }

    private Map<String, OAuthClientRegistration> createRegistrationMap(
        OAuthRegistrationProperties oAuthRegistrationProperties,
        OAuthAttributesConverters converters) {

        Map<String, OAuthClientRegistration> registrationMap = new HashMap<>();
        oAuthRegistrationProperties.getRegistrations()
            .forEach((registrationId, registrationProperty) -> registrationMap.put(registrationId,
                    OAuthClientRegistration.of(registrationId, registrationProperty,
                        converters.findByConverterId(registrationId))));

        return registrationMap;
    }

}
