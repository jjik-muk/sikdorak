package com.jjikmuk.sikdorak.common.util.feignclient;

import feign.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FeignClientResponseUtils {
    public static String getRequestBody(Response response) {
        if (response.request().body() == null) {
            return "";
        }

        return new String(response.request().body(), StandardCharsets.UTF_8);
    }

    public static String getResponseBody(Response response) {
        if (response.body() == null) {
            return "";
        }

        try (InputStream responseBodyStream = response.body().asInputStream()) {
            return IOUtils.toString(responseBodyStream, String.valueOf(StandardCharsets.UTF_8));

        } catch (IOException e) {
            return "";
        }
    }
}
