package com.jjikmuk.sikdorak.tool;

import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;
import java.io.InputStream;

public abstract class TestResourceUtils {

	public static String getTestResourceWithPath(String path) {
		try {
			return copyToString(getResourceStreamWithPath(path), defaultCharset());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static InputStream getResourceStreamWithPath(String path) {
		return TestResourceUtils.class.getClassLoader().getResourceAsStream(path);
	}
}
