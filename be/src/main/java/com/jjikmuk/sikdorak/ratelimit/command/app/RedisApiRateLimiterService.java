package com.jjikmuk.sikdorak.ratelimit.command.app;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisApiRateLimiterService implements ApiRateLimiterService{

	private final StringRedisTemplate redisTemplate;

	/**
	 *  클라이언트 ip를 활용하여 요청하는 api에 대해 rate limit을 확인한다.
	 *  가능한 경우 해당 api 사용횟수를 1회 증가시킨다.
	 *  불가능한 경우 Exception을 발생시킨다.
	 *
	 * @param request 클라이언트의 ip, servlet path를 사용한다.
	 * @param rangeMinutes api 사용 빈도 검색 시간 범위
	 * @param apiMaximumNumber rangeMinutes 내 최대 api 요청수
	 *
	 * @return true : api 사용 가능
	 */
	@Override
	public Boolean checkRateLimit(HttpServletRequest request, Long rangeMinutes, Long apiMaximumNumber) {
		throw new UnsupportedOperationException(
			"RedisApiRateLimiterService#checkRateLimit 아직 구현하지 않음 !!");
	}
}
