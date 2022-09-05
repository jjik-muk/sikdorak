package com.jjikmuk.sikdorak.acceptance;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.utility.DockerImageName;

/**
 * Reference : https://www.testcontainers.org/modules/localstack/
 */
@TestConfiguration
public class AWSMockConfig {

	@Bean(initMethod = "start", destroyMethod = "stop")
	public LocalStackContainer localStackContainer() {
		DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:0.11.3");

		return new LocalStackContainer(localstackImage)
			.withServices(LocalStackContainer.Service.S3);
	}

	@Bean
	public AmazonS3 amazonS3(LocalStackContainer localStack) {
		return AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration(
				new AwsClientBuilder.EndpointConfiguration(
					localStack.getEndpointOverride(Service.S3).toString(),
					localStack.getRegion()
				)
			)
			.withCredentials(
				new AWSStaticCredentialsProvider(
					new BasicAWSCredentials(localStack.getAccessKey(), localStack.getSecretKey())
				)
			)
			.build();
	}

}
