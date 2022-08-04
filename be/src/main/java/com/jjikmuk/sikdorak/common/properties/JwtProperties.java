package com.jjikmuk.sikdorak.common.properties;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "secret")
public class JwtProperties {

    @NotEmpty
    private final byte[] secretKey;
    @NotNull
    private final long accessTokenExpiredMillisecond;
    @NotNull
    private final long refreshTokenExpiredMillisecond;

    public JwtProperties(String secretKey, long accessTokenExpiredMillisecond, long refreshTokenExpiredMillisecond) {
        this.secretKey = secretKey.getBytes(StandardCharsets.UTF_8);
        this.accessTokenExpiredMillisecond = accessTokenExpiredMillisecond;
        this.refreshTokenExpiredMillisecond = refreshTokenExpiredMillisecond;
    }
}
