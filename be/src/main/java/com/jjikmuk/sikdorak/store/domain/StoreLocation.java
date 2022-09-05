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

    private static final double LATITUDE_MIN = -90.0;
    private static final double LATITUDE_MAX = 90.0;
    private static final double LONGITUDE_MIN = -180.0;
    private static final double LONGITUDE_MAX = 180.0;

    private Double x;
    private Double y;

    public StoreLocation(Double x, Double y) {
        if (Objects.isNull(y) ||
                Objects.isNull(x) ||
            isNotInRange(y, LATITUDE_MIN, LATITUDE_MAX) ||
            isNotInRange(x, LONGITUDE_MIN, LONGITUDE_MAX)) {
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
