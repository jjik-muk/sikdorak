package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidContactNumberException;
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
public class ContactNumber {

	/**
	 * 허용되는 전화번호 형식 <br> - 00-000-0000 <br> - 00-0000-0000 <br> - 000-000-0000 <br> - 000-0000-0000
	 */

	private static final int LIMIT_LENGTH = 13;
	private static final Pattern numberPattern = Pattern.compile("\\d{2,3}-\\d{3,4}-\\d{4}");

	@Column(length = LIMIT_LENGTH)
	private String contactNumber;

	public ContactNumber(String contactNumber) {

		if (Objects.nonNull(contactNumber) && (contactNumber.isBlank()
			|| contactNumber.length() > LIMIT_LENGTH
			|| !isPatternMatches(contactNumber, numberPattern))) {
			throw new InvalidContactNumberException();
		}

		this.contactNumber = contactNumber;
	}

	private boolean isPatternMatches(String contactNumber, Pattern pattern) {
		return pattern.matcher(contactNumber).matches();
	}
}
