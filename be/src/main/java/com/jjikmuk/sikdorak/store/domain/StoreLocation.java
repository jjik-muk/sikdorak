package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidStoreLocationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreLocation {

    private static final double X_MIN = -180.0; // longitude(경도)
    private static final double X_MAX = 180.0;
    private static final double Y_MIN = -90.0; // latitude(위도)
    private static final double Y_MAX = 90.0;

    private Double x;
    private Double y;

    public StoreLocation(Double x, Double y) {
        if (Objects.isNull(y) ||
                Objects.isNull(x) ||
            isNotInRange(x, X_MIN, X_MAX) ||
            isNotInRange(y, Y_MIN, Y_MAX)) {
            throw new InvalidStoreLocationException();
        }

        // X = longitude, Y = latitude
        this.x = x;
        this.y = y;
    }

    private boolean isNotInRange(double value, double min, double max) {
        return !(min <= value) || !(value <= max);
    }
}
