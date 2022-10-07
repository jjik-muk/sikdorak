package com.jjikmuk.sikdorak.image.command.domain;

import com.jjikmuk.sikdorak.image.exception.InvalidImageOriginUrlException;
import java.util.Objects;
import lombok.Getter;

/**
 * ImageFileName 객체는 s3의 이미지 url을 입력 받아 ImageFileName만 추출하는 객체이다.
 */
@Getter
public class ImageFileName {

	private final String fileName;

	public ImageFileName(String imageOriginUrl) {
		if (Objects.isNull(imageOriginUrl) || !imageOriginUrl.contains(".")) {
			throw new InvalidImageOriginUrlException();
		}

		String parseFileName = imageOriginUrl.substring(imageOriginUrl.lastIndexOf('/') + 1);
		ImageExtension.create(parseFileName.split("\\.")[1]);

		this.fileName = parseFileName;
	}
}
