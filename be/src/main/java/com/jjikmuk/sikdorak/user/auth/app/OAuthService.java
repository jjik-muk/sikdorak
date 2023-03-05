package com.jjikmuk.sikdorak.user.auth.app;

import com.jjikmuk.sikdorak.common.properties.KakaoProperties;
import com.jjikmuk.sikdorak.common.properties.oauth.ClientRegistrationRepository;
import com.jjikmuk.sikdorak.common.properties.oauth.OAuthClientRegistration;
import com.jjikmuk.sikdorak.user.auth.app.dto.JwtTokenPair;
import com.jjikmuk.sikdorak.user.auth.app.response.KakaoAccountResponse;
import com.jjikmuk.sikdorak.user.auth.app.response.OAuthTokenResponse;
import com.jjikmuk.sikdorak.user.user.command.app.UserService;
import com.jjikmuk.sikdorak.user.user.command.domain.User;
import com.jjikmuk.sikdorak.user.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class OAuthService {

    private final OAuthTokenClient oAuthTokenClient;
    private final OAuthApiClient oAuthApiClient;
    private final KakaoProperties kakaoProperties;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final ClientRegistrationRepository registrationRepository;

    public String getLoginPageUrl(String registrationId) {
        OAuthClientRegistration registration = registrationRepository.findRegistrationByName(
            registrationId);

        return registration.getAuthorizationUri();
    }

    @Transactional
    public JwtTokenPair login(String code) {
        OAuthTokenResponse oAuthTokenResponse = getOAuthAccessToken(code);
        KakaoAccountResponse userInfo = getOAuthUserInformation(oAuthTokenResponse);

        User user;
        if (!userService.isExistingByUniqueId(userInfo.getUniqueId())) {
            user = new User(userInfo.getUniqueId(), userInfo.getNickname(),
                userInfo.getProfileImage(), userInfo.getEmail());
            userService.createUser(user);
            return jwtProvider.createTokenResponse(String.valueOf(user.getId()));
        }
        user = userService.searchByUniqueId(userInfo.getUniqueId());
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

    private OAuthTokenResponse getOAuthAccessToken(String code) {
        return oAuthTokenClient.getAccessToken(
            kakaoProperties.getGrantType(),
            kakaoProperties.getClientId(),
            kakaoProperties.getRedirectUri(),
            code);
    }

    private KakaoAccountResponse getOAuthUserInformation(OAuthTokenResponse oAuthTokenResponse) {
        String authorizationHeader = String.format("%s %s", oAuthTokenResponse.getTokenType(),
            oAuthTokenResponse.getAccessToken());
        return oAuthApiClient.getUserInfo(authorizationHeader);
    }
}
