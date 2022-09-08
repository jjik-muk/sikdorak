package com.jjikmuk.sikdorak.store.service.client;

import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientKakaoAuthHeaderConfiguration;
import com.jjikmuk.sikdorak.common.config.feignclient.FeignClientOAuthErrorConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "local-address-client", url="${api.kakao.api_url}", configuration = {
	FeignClientKakaoAuthHeaderConfiguration.class,
	FeignClientOAuthErrorConfiguration.class})
public interface KakaoLocalAddressSearchClient {

	@GetMapping("/v2/local/search/address.json")
	KakaoAddressSearchResponse searchAddress(@RequestParam("query") String query);
}
