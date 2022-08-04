package com.jjikmuk.sikdorak.integration.auth;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.URISyntaxException;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class OAuthMocks {
    public static void setupMockTokenResponse(WireMockServer wireMockServer) throws IOException {
        wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/oauth/token?grant_type=authorization_code&client_id=1234&redirect_uri=redirectUri&code=code"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        OAuthMocks.class.getClassLoader().getResourceAsStream("payload/get-token-response.json"),
                                        defaultCharset()))));
    }

    public static void setupMockUserInfoResponse(WireMockServer wireMockServer) throws IOException, URISyntaxException {
        wireMockServer.stubFor(WireMock.get(WireMock.urlEqualTo("/v2/user/me"))
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(
                                copyToString(
                                        OAuthMocks.class.getClassLoader().getResourceAsStream("payload/get-user-info-response.json"),
                                        defaultCharset()))));
    }
}
