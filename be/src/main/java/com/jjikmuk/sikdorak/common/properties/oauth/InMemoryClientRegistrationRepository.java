package com.jjikmuk.sikdorak.common.properties.oauth;

import com.jjikmuk.sikdorak.user.auth.exception.ClientRegistrationNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.Getter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

@Getter
@ConfigurationProperties("oauth")
public class InMemoryClientRegistrationRepository implements InitializingBean, ClientRegistrationRepository {

    private final Map<String, OAuthClientRegistration> registrations = new HashMap<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        validate();
    }
    
    @Override
    public OAuthClientRegistration findRegistrationByName(String registrationId){
        if(Objects.isNull(registrations.get(registrationId.toLowerCase()))){
            throw new ClientRegistrationNotFoundException();
        }
        return registrations.get(registrationId.toLowerCase());
    }

    private void validate() {
        registrations.forEach((registrationId, registration) ->  {
            if(!StringUtils.hasText(registrationId)){
                throw new IllegalStateException("등록정보에 플랫폼명이 존재하지 않습니다.");
            }
            if(Objects.isNull(registration)){
                throw new NullPointerException("등록된 Registration이 존재하지 않습니다.");
            }
        });
    }

}
