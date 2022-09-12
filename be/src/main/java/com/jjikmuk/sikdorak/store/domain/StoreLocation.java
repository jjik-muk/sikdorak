package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidStoreLocationException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreLocation {

    private static final double X_MIN = -180.0; // longitude(경도)
    private static final double X_MAX = 180.0;
    private static final double Y_MIN = -90.0; // latitude(위도)
    private static final double Y_MAX = 90.0;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
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
