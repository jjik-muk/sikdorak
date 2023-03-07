package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.common.oauth.OAuthClientRegistration;
import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthAuthenticationRequest;
import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.app.dto.OAuthUserProfile;
import com.jjikmuk.sikdorak.user.auth.app.response.OAuthAccessTokenResponse;
import com.jjikmuk.sikdorak.user.auth.app.response.OAuthUserResponse;
import com.jjikmuk.sikdorak.user.user.command.app.UserService;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {

    private final OAuthClient oAuthClient;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Transactional
    public JwtTokenPair login(OAuthAuthenticationRequest authenticationRequest) {

        OAuthAccessTokenResponse oAuthAccessTokenResponse = getOAuthAccessToken(authenticationRequest);
        OAuthUserResponse oAuthUserResponse = getOAuthUserInformation(authenticationRequest,
            oAuthAccessTokenResponse);
        OAuthClientRegistration registration = authenticationRequest.getRegistration();
        OAuthUserProfile oAuthUserProfile = registration.convert(oAuthUserResponse.getUserInfo());

        if (!userService.isExistingByUniqueId(oAuthUserProfile.getUniqueId())) {
            User user = oAuthUserProfile.toEntity();
            userService.createUser(user);
            return jwtProvider.createTokenResponse(String.valueOf(user.getId()));
        }

        User user = userService.searchByUniqueId(oAuthUserProfile.getUniqueId());
        return jwtProvider.createTokenResponse(String.valueOf(user.getId()));
    }

    @Transactional(readOnly = true)
    public JwtTokenPair updateAccessAndRefreshToken(String refreshToken) {

        jwtProvider.validateToken(refreshToken);
        String userId = jwtProvider.decodeToken(refreshToken);

        if (!userService.isExistingById(Long.parseLong(userId))) {
            throw new NotFoundUserException();
        }

        return jwtProvider.createTokenResponse(userId);
    }

    private OAuthAccessTokenResponse getOAuthAccessToken(OAuthAuthenticationRequest authenticationRequest) {
        OAuthClientRegistration registration = authenticationRequest.getRegistration();
        URI tokenUrl = URI.create(registration.getTokenUrl());
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", registration.getGrantType());
        body.add("client_id", registration.getClientId());
        body.add("client_secret", registration.getClientSecret());
        body.add("redirect_uri", registration.getRedirectUrl());
        body.add("code", authenticationRequest.getAuthorizationCode());

        return oAuthClient.getAccessToken(tokenUrl, body);
    }

    private OAuthUserResponse getOAuthUserInformation(OAuthAuthenticationRequest oAuthAuthenticationRequest,
        OAuthAccessTokenResponse oAuthAccessTokenResponse) {

        URI userInfoUrl = URI.create(oAuthAuthenticationRequest.getRegistration().getUserInfoUrl());
        String authorizationHeader = String.format("%s %s",
            oAuthAccessTokenResponse.getTokenType(),
            oAuthAccessTokenResponse.getAccessToken());

        return oAuthClient.getUserInfo(userInfoUrl, authorizationHeader);
    }
}
