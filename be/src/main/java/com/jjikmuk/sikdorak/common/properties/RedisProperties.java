package com.jjikmuk.sikdorak.common.properties;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {

	@NotEmpty
	private final String host;

	@NotNull
	@Range(min = 0, max = 65535)
	private final int port;

	public RedisProperties(String host, int port) {
		this.host = host;
		this.port = port;
	}
}
