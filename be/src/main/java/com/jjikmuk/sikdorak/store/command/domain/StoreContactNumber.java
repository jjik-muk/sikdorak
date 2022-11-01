package com.jjikmuk.sikdorak.store.command.domain;

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
public class StoreContactNumber {

	private static final int LIMIT_LENGTH = 13;
	private static final Pattern numberPattern = Pattern.compile("\\d{2,3}-\\d{3,4}-\\d{4}");

	@Column(length = LIMIT_LENGTH)
	private String contactNumber;

	/*
	 * 허용되는 전화번호 형식 <br> - 00-000-0000 <br> - 00-0000-0000 <br> - 000-000-0000 <br> - 000-0000-0000
	 */
	public StoreContactNumber(String contactNumber) {

		if (notEmptyContactNumber(contactNumber)
			&& invalidContactNumber(contactNumber)) {
			throw new InvalidContactNumberException();
		}

		// 전화번호가 공백이면, 공백 문자열 대신 null 로 저장되도록 함
		// (전화번호 없는 가게들의 통일성을 위해서)
		if (Objects.nonNull(contactNumber) && contactNumber.isBlank()) {
			contactNumber = null;
		}

		this.contactNumber = contactNumber;
	}

	private boolean invalidContactNumber(String contactNumber) {
		return contactNumber.length() > LIMIT_LENGTH
			|| !isPatternMatches(contactNumber, numberPattern);
	}

	private static boolean notEmptyContactNumber(String contactNumber) {
		return Objects.nonNull(contactNumber) && !contactNumber.isBlank();
	}

	private boolean isPatternMatches(String contactNumber, Pattern pattern) {
		return pattern.matcher(contactNumber).matches();
	}
}
