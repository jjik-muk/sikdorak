package com.jjikmuk.sikdorak.common.properties.oauth;

public interface ClientRegistrationRepository {

    OAuthClientRegistration findRegistrationByName(String name);

}
