package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidAddressException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {

    private static final int BASEADDRESS_LENGTH = 255;
    private static final int DETAILADDRESS_LENGTH = 50;

    private String baseAddress;

    private String detailAddress;

    public Address(String baseAddress) {
        this(baseAddress, null);
    }

    public Address(String baseAddress, String detailAddress) {
        validateBaseAddress(baseAddress);
        validateDetailAddress(detailAddress);

        this.baseAddress = baseAddress;
        this.detailAddress = detailAddress;
    }

    private void validateBaseAddress(String baseAddress) {
        // baseAddress 는 not null, not empty, 255자 제한, 한글 숫자만
        if (Objects.isNull(baseAddress) ||
                baseAddress.isBlank() ||
                baseAddress.length() > BASEADDRESS_LENGTH ||
                !isContainsOnlyKoreanAndNumber(baseAddress)) {
            throw new InvalidAddressException();
        }
    }

    private void validateDetailAddress(String detailAddress) {
        // detailAddress 는 null 이 아닌 경우, not empty, 50자 제한, 한글 숫자만
        if (Objects.nonNull(detailAddress) &&
                (detailAddress.isBlank() ||
                        detailAddress.length() > BASEADDRESS_LENGTH ||
                        !isContainsOnlyKoreanAndNumber(detailAddress))
        ) {
            throw new InvalidAddressException();
        }
    }

    private boolean isContainsOnlyKoreanAndNumber(String text) {
        return Pattern.matches("^[가-힣 0-9]*$", text);
    }
}
