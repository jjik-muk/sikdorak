package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidStoreLocationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreLocation {

    private static final double LATITUDE_MIN = -90.0;
    private static final double LATITUDE_MAX = 90.0;
    private static final double LONGITUDE_MIN = -180.0;
    private static final double LONGITUDE_MAX = 180.0;

    private Point storePoint;

    public StoreLocation(Double latitude, Double longitude) {
        if (Objects.isNull(latitude) ||
                Objects.isNull(longitude) ||
                !isInRange(latitude, LATITUDE_MIN, LATITUDE_MAX) ||
                !isInRange(longitude, LONGITUDE_MIN, LONGITUDE_MAX)) {
            throw new InvalidStoreLocationException();
        }

        // longitude = X, latitude = Y
        this.storePoint = new Point(longitude, latitude);
    }

    private boolean isInRange(double value, double min, double max) {
        return min <= value && value <= max;
    }

    public double latitude() {
        return storePoint.getY();
    }

    public double longitude() {
        return storePoint.getX();
    }
}
