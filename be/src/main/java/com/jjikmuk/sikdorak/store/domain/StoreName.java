package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidStoreNameException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class StoreName {

    private static final int LIMIT_LENGTH = 50;

    @Column(length = LIMIT_LENGTH)
    private String storeName;

    public StoreName(String storeName) {
        if (Objects.isNull(storeName) ||
                storeName.isBlank() ||
                storeName.length() > LIMIT_LENGTH) {
            throw new InvalidStoreNameException();
        }

        this.storeName = storeName;
    }
}
