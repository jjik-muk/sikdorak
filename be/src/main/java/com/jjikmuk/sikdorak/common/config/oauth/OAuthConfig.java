package com.jjikmuk.sikdorak.common.config.oauth;

import com.jjikmuk.sikdorak.common.oauth.OAuthAttributesConverters;
import com.jjikmuk.sikdorak.common.properties.oauth.OAuthRegistrationProperties;
import com.jjikmuk.sikdorak.common.oauth.ClientRegistrationRepository;
import com.jjikmuk.sikdorak.common.oauth.InMemoryClientRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OAuthConfig {

    private final OAuthAttributesConverters oAuthAttributesConverters;
    private final OAuthRegistrationProperties oAuthRegistrationProperties;

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(oAuthRegistrationProperties, oAuthAttributesConverters);
    }

}
