package com.jjikmuk.sikdorak.common.mock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.stubbing.Scenario.STARTED;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class OAuthMocks {

    public static void setUpResponses() throws IOException {
        setupMockTokenResponse();
        setupMockUserInfoResponse();
        setupMockUserInfoWithNoEmailReponse();
    }

    public static void setupMockTokenResponse() throws IOException {
        stubFor(post(urlEqualTo("/oauth/token?grant_type=authorization_code&client_id=1234&redirect_uri=redirectUri&code=code"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getMockResponseBodyByPath("payload/get-token-response.json"))
                )
        );
    }

    public static void setupMockUserInfoResponse() throws IOException {
        stubFor(get(urlEqualTo("/v2/user/me"))
            .inScenario("Email Not Null")
            .whenScenarioStateIs(STARTED)
                .withHeader("Authorization", equalTo("bearer accessToken"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(getMockResponseBodyByPath("payload/get-user-info-response.json"))
                )
        );
    }

    public static void setupMockUserInfoWithNoEmailReponse() throws IOException{
        stubFor(get(urlEqualTo("/v2/user/me"))
            .inScenario("Email Null")
            .whenScenarioStateIs(STARTED)
            .withHeader("Authorization", equalTo("bearer accessToken"))
            .willReturn(aResponse()
                .withStatus(HttpStatus.OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(getMockResponseBodyByPath("payload/get-user-info-with-no-email-response.json"))
            )
        );
    }

    private static String getMockResponseBodyByPath(String path) throws IOException {
        return copyToString(getMockResourceStream(path), defaultCharset());
    }

    private static InputStream getMockResourceStream(String path) {
        return OAuthMocks.class.getClassLoader().getResourceAsStream(path);
    }
}
