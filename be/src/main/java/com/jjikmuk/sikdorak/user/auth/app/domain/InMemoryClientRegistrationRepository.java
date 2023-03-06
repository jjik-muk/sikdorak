package com.jjikmuk.sikdorak.user.auth.app.domain;

import com.jjikmuk.sikdorak.common.properties.oauth.OAuthRegistrationProperties;
import com.jjikmuk.sikdorak.user.auth.exception.ClientRegistrationNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InMemoryClientRegistrationRepository implements ClientRegistrationRepository {

    private final Map<String, OAuthClientRegistration> registrations;

    public InMemoryClientRegistrationRepository(OAuthRegistrationProperties oAuthRegistrationProperties) {
        this.registrations = createRegistrationMap(oAuthRegistrationProperties);
    }

    @Override
    public OAuthClientRegistration findRegistrationByName(String registrationId){
        if(Objects.isNull(registrations.get(registrationId.toLowerCase()))){
            throw new ClientRegistrationNotFoundException();
        }
        return registrations.get(registrationId.toLowerCase());
    }

    private Map<String, OAuthClientRegistration> createRegistrationMap(
        OAuthRegistrationProperties oAuthRegistrationProperties) {
        Map<String, OAuthClientRegistration> registrationMap = new HashMap<>();
        oAuthRegistrationProperties.getRegistrations()
            .forEach((registrationId, registrationProperty) -> registrationMap.put(registrationId,
                OAuthClientRegistration.of(registrationId, registrationProperty)));
        return registrationMap;
    }

}
