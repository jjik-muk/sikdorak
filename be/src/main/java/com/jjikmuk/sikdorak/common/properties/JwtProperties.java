package com.jjikmuk.sikdorak.common.properties;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.nio.charset.StandardCharsets;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "secret")
public class JwtProperties {

    private byte[] secretKey;
    private long accessTokenExpiredMillisecond;
    private long refreshTokenExpiredMillisecond;

    public JwtProperties(String secretKey, long accessTokenExpiredMillisecond, long refreshTokenExpiredMillisecond) {
        this.secretKey = secretKey.getBytes(StandardCharsets.UTF_8);
        this.accessTokenExpiredMillisecond = accessTokenExpiredMillisecond;
        this.refreshTokenExpiredMillisecond = refreshTokenExpiredMillisecond;
    }
}
