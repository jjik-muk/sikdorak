package com.jjikmuk.sikdorak.user.auth.app.domain;

public interface ClientRegistrationRepository {

    OAuthClientRegistration findRegistrationByName(String name);

}
