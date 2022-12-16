package com.jjikmuk.sikdorak.integration.ratelimit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

import com.jjikmuk.sikdorak.ratelimit.command.app.ApiRateLimiterService;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;

@DisplayName("통합 테스트 : 특정 api에 대한 RateLimit 확인")
@SpringBootTest // 임시
@ActiveProfiles("test") // 임시
class RedisApiRateLimiterServiceTest {

	static final GenericContainer redis;

	static {
		redis = new GenericContainer("redis:7.0.5")
			.withExposedPorts(6379);
		redis.start();
		System.setProperty("spring.redis.host", redis.getHost());
		System.setProperty("spring.redis.port", redis.getMappedPort(6379).toString());
	}

	@Autowired
	private ApiRateLimiterService apiRateLimiterService;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@AfterEach
	void setUp() {
		Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushAll();
	}

	@Test
	@DisplayName("클라이언트가 api 요청 최대 이하로 요청한다면, api에 요청할 수 있다.")
	void api_request_less_than_maximum_number() throws InterruptedException {
		// given
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/api/images/url");
		int threadCount = 8;
		ExecutorService executorService = Executors.newFixedThreadPool(16);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					apiRateLimiterService.checkRateLimit(request, 60 * 5L, threadCount + 1L);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		Boolean result = apiRateLimiterService.checkRateLimit(request, 60 * 5L, threadCount + 1L);

		// then
		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("클라이언트가 api 요청 최대개수 -1으로 요청하고 만료 시간이 지난 뒤 요청해도 api에 요청할 수 있다.")
	void api_request_less_than_maximum_number_and_sleep() throws InterruptedException {
		// given
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/api/images/url");
		int threadCount = 8;
		ExecutorService executorService = Executors.newFixedThreadPool(16);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					apiRateLimiterService.checkRateLimit(request, 60 * 5L, threadCount + 1L);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		await().atMost(10, TimeUnit.SECONDS).until(() -> true); // 10초 대기
		Boolean result = apiRateLimiterService.checkRateLimit(request, 60 * 5L, threadCount + 1L);

		assertThat(result).isTrue();
	}

	@Test
	@DisplayName("클라이언트가 api 요청 최대 이상으로 요청한다면, 예외를 발생시킨다")
	void api_request_more_than_maximum_number() throws InterruptedException {
		// given
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setServletPath("/api/images/url");
		int threadCount = 9;
		ExecutorService executorService = Executors.newFixedThreadPool(16);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// when
		for (int i = 0; i < threadCount; i++) {
			executorService.submit(() -> {
				try {
					apiRateLimiterService.checkRateLimit(request, 60 * 5L, threadCount + 1L);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();

		assertThatThrownBy(() -> apiRateLimiterService.checkRateLimit(request, 60 * 5L, threadCount + 1L))
			.isInstanceOf(ApiRateLimiterService.class);
	}

}
