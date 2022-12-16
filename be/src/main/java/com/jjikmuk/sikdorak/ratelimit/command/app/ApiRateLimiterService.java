package com.jjikmuk.sikdorak.ratelimit.command.app;

import javax.servlet.http.HttpServletRequest;

public interface ApiRateLimiterService {

	Boolean checkRateLimit(HttpServletRequest request, Long rangeSeconds, Long apiMaximumNumber);

}
