package com.jjikmuk.sikdorak.common.extension;

import com.jjikmuk.sikdorak.common.mock.KakaoPlaceMocks;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class PlaceMocksExtention implements BeforeAllCallback, AfterAllCallback {

	@Override
	public void afterAll(ExtensionContext context) {
		KakaoPlaceMocks.startSearchPlacesMockScenario();
	}

	@Override
	public void beforeAll(ExtensionContext context) {
		KakaoPlaceMocks.resetSearchPlacesMockScenario();
	}
}
