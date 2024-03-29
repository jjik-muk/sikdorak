package com.jjikmuk.sikdorak.tool.extension;

import com.jjikmuk.sikdorak.tool.mock.KakaoPlaceMocks;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class PlaceMocksExtention implements BeforeAllCallback, AfterAllCallback {

	@Override
	public void beforeAll(ExtensionContext context) {
		KakaoPlaceMocks.startAllMocks();
	}

	@Override
	public void afterAll(ExtensionContext context) {
		KakaoPlaceMocks.resetAllMocks();
	}
}
