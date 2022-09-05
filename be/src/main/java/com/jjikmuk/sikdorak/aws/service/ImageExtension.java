package com.jjikmuk.sikdorak.aws.service;

import com.jjikmuk.sikdorak.aws.service.exception.InvalidImagesExtensionException;

public enum ImageExtension {
	JPG(".jpg"), JPEG(".jpeg"), PNG(".png");

	private final String extension;

	ImageExtension(String extension) {
		this.extension = extension;
	}

	public static ImageExtension create(String extension) {
		try {
			return valueOf(extension.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new InvalidImagesExtensionException(e);
		}
	}

	public String getExtension() {
		return extension;
	}
}