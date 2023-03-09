package com.jjikmuk.sikdorak.common.oauth;

public interface ClientRegistrationRepository {

    OAuthClientRegistration findRegistrationByName(String name);

}
