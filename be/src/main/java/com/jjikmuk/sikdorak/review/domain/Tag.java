package com.jjikmuk.sikdorak.review.domain;

import com.jjikmuk.sikdorak.review.exception.InvalidTagException;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tag {

	public static final int LIMIT_LENGTH = 50;
	private final String tag;

	public Tag(String tag) {

		if (Objects.isNull(tag) ||
			tag.isBlank() ||
			tag.length() > LIMIT_LENGTH ||
			hasSymbols(tag)) {
			throw new InvalidTagException();
		}

		this.tag = tag.toLowerCase(Locale.ROOT);
	}

	private boolean hasSymbols(String tag) {
		Pattern compile = Pattern.compile("[\\s!@#$%^&*\\+\\-(),.?\":{}|<>]");
		Matcher matcher = compile.matcher(tag);
		return matcher.find();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Tag tag1 = (Tag) o;
		return tag.equals(tag1.tag);
	}

	@Override
	public int hashCode() {
		return Objects.hash(tag);
	}

	public String getText() {
		return tag;
	}
}
