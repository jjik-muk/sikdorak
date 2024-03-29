package com.jjikmuk.sikdorak.image.command.domain;

import com.jjikmuk.sikdorak.image.exception.InvalidImagesExtensionException;

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
