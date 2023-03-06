package com.jjikmuk.sikdorak.common.config;

import com.jjikmuk.sikdorak.common.properties.oauth.OAuthRegistrationProperties;
import com.jjikmuk.sikdorak.user.auth.app.domain.ClientRegistrationRepository;
import com.jjikmuk.sikdorak.user.auth.app.domain.InMemoryClientRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OAuthConfig {

    private final OAuthRegistrationProperties oAuthRegistrationProperties;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(oAuthRegistrationProperties);
    }

}
