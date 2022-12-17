package com.jjikmuk.sikdorak.integration;

import com.amazonaws.services.s3.AmazonS3;
import com.jjikmuk.sikdorak.common.properties.AwsProperties;
import com.jjikmuk.sikdorak.tool.DatabaseConfigurator;
import com.jjikmuk.sikdorak.tool.mock.AWSMockConfig;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;


@SpringBootTest(classes = AWSMockConfig.class)
@ActiveProfiles("test")
public abstract class InitIntegrationTest {

	static final GenericContainer redis;

	static {
		redis = new GenericContainer("redis:7.0.5")
			.withExposedPorts(6379);
		redis.start();
	}

	@DynamicPropertySource
	public static void overrideProps(DynamicPropertyRegistry registry) {
		registry.add("spring.redis.host", redis::getHost);
		registry.add("spring.redis.port", () -> redis.getMappedPort(6379));
	}

	@Autowired
	protected DatabaseConfigurator testData;

	@Autowired
	protected AmazonS3 amazonS3;

	@Autowired
	protected AwsProperties awsProperties;

	@BeforeEach
	void setUpDataBase() {
		testData.clear();
		testData.initDataSource();
	}
}
