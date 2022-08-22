package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidReviewImageException;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

	private static final String URL_REGEX = "^(((https?|http?)://)(%[0-9A-Fa-f]{2}|[-()_.!~*';/?:@&=+$,A-Za-z0-9])+)([).!';/?:,][:blank])?$";

	@Column(name = "image_url")
	private String url;

	public Image(String url) {
		if (Objects.isNull(url) ||
			url.isBlank() ||
			!validateUrlForm(url)) {
			throw new InvalidReviewImageException();
		}
		this.url = url;
	}

	// TODO : 특정 s3 주소만 image로 들어올 수 있도록 수정 가능하다.
	private boolean validateUrlForm(String url) {
		return Pattern.matches(URL_REGEX, url);
	}
}
