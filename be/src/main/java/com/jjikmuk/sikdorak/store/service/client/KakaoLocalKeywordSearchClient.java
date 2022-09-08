package com.jjikmuk.sikdorak.store.service.client;

import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientKakaoApiErrorConfiguration;
import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientKakaoAuthHeaderConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "local-keyword-client", url = "${api.kakao.api_url}", configuration = {
	FeignClientKakaoAuthHeaderConfiguration.class,
	FeignClientKakaoApiErrorConfiguration.class})
public interface KakaoLocalKeywordSearchClient {

	@GetMapping("/v2/local/search/keyword.json")
	KakaoPlaceSearchResponse searchPlaceWithKeyword(
		@RequestParam("query") String query,
		@RequestParam(value = "x", required = false) Double x,
		@RequestParam(value = "y", required = false) Double y,
		@RequestParam(value = "radius", required = false) Integer radius
	);
}
