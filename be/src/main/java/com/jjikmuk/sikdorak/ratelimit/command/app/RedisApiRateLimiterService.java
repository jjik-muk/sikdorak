package com.jjikmuk.sikdorak.ratelimit.command.app;

import com.jjikmuk.sikdorak.ratelimit.exception.ApiLimitExceededException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisApiRateLimiterService implements ApiRateLimiterService {

	private final StringRedisTemplate redisTemplate;

	/**
	 * Redis 저장소를 활용하여 Rate Limit 여부를 확인한다.
	 * Redis Key Format - API:ip:yyyyMMddHHmm , e.g. /api/images/url:127.0.0.1:202212161449
	 * API 사용 가능한 경우 해당 key의 값을 1 증가시킨다.
	 * 불가능한 경우 Exception을 발생시킨다.
	 *
	 * @param request          클라이언트의 ip, servlet path를 사용한다.
	 * @param rangeMinutes     api 사용 빈도 검색 시간 범위 & Redis Key expire time
	 * @param apiMaximumNumber rangeMinutes 내 최대 api 요청수
	 * @return true : api 사용 가능
	 */
	@Override
	public Boolean checkRateLimit(HttpServletRequest request, long rangeMinutes, long apiMaximumNumber) {
		Date currentDate = new Date();
		String apiPathAndIpKey = getApiPathAndIpKey(request);

		long totalApiCounts = findTotalApiCounts(apiPathAndIpKey, currentDate, rangeMinutes);

		// 검증
		if (totalApiCounts < apiMaximumNumber) {
			upsertApiCount(apiPathAndIpKey, currentDate, rangeMinutes);
			return true;
		}

		throw new ApiLimitExceededException();
	}

	private String getApiPathAndIpKey(HttpServletRequest request) {
		String servletPath = request.getServletPath();
		String clientIp = getClientIp(request); // ip 가져올 때 ipv4 vs ipv6

		StringBuilder sb = new StringBuilder();
		sb.append(servletPath).append(":").append(clientIp).append(":");

		return sb.toString();
	}

	/**
	 * 클라이언트의 ip 주소를 추출한다.
	 * ref : <a href="https://www.lesstif.com/software-architect/proxy-client-ip-x-forwarded-for-xff-http-header-20775886.html">...</a>.
	 *
	 * @param request Client의 HttpServletRequest
	 * @return Client Ip
	 */
	private String getClientIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}

	/**
	 * 현재 시간부터 rangeMinutes 이전까지의 시간의 api counts 총합을 리턴합니다.
	 *
	 * @param apiPathAndIpKey - /api/images/url:127.0.0.1
	 * @param currentDate - 현재 시간의 Date 객체
	 * @param rangeMinutes - api 사용 빈도 검색 시간 범위
	 * @return totalApiCounts
	 */
	private long findTotalApiCounts(String apiPathAndIpKey, Date currentDate, long rangeMinutes) {
		// 현재 시간 key 기준 검색할 key List e.g. 202212161449, 202212161448, 202212161447 ...
		List<String> searchRateLimitKeys = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		for (int i = 0; i < rangeMinutes; i++) {
			searchRateLimitKeys.add(apiPathAndIpKey + new SimpleDateFormat("yyyyMMddHHmm").format(calendar.getTime()));
			calendar.add(Calendar.MINUTE, -1);
		}

		// 전체 카운트 조회
		ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
		List<String> values = valueOperations.multiGet(searchRateLimitKeys);
		long totalCounts = 0L;
		for (String value : Objects.requireNonNull(values)) {
			if (Objects.nonNull(value)) {
				totalCounts = totalCounts + Long.parseLong(value);
			}
		}

		return totalCounts;
	}

	/**
	 * Redis Key의 value를 1 증가 시키고 expire time을 설정한다.
	 *
	 * @param apiPathAndIpKey - /api/images/url:127.0.0.1
	 * @param currentDate - 현재 시간의 Date 객체
	 * @param rangeMinutes - api 사용 빈도 검색 시간 범위 & Redis Key expire time
	 */
	private void upsertApiCount(String apiPathAndIpKey, Date currentDate, long rangeMinutes) {
		String currentRateLimitKey =
			apiPathAndIpKey + new SimpleDateFormat("yyyyMMddHHmm").format(currentDate);

		redisTemplate.execute(new SessionCallback<>() {
			@Override
			public <K, V> Object execute(RedisOperations<K, V> operations)
				throws DataAccessException {

				operations.multi();
				operations.opsForValue().increment((K) currentRateLimitKey);
				operations.expire((K) currentRateLimitKey, rangeMinutes, TimeUnit.MINUTES);
				return operations.exec();
			}
		});
	}
}
