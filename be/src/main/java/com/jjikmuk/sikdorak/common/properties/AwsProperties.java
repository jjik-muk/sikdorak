package com.jjikmuk.sikdorak.common.properties;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.aws.s3")
public class AwsProperties {

	@NotEmpty
	private final String bucket;

	public AwsProperties(String bucket) {
		this.bucket = bucket;
	}

	public String getBucket() {
		return bucket;
	}
}
