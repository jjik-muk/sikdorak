package com.jjikmuk.sikdorak.store.domain;

import com.jjikmuk.sikdorak.store.exception.InvalidUserLocationException;

public record UserLocationInfo(double x, double y, int radius) {

    private static final double MAX_LATITUDE_RANGE = 90.0;
    private static final double MIN_LATITUDE_RANGE = -90.0;
    private static final double MAX_LONGITUDE_RANGE = 180.0;
    private static final double MIN_LONGITUDE_RANGE = -180.0;
    private static final int MAX_RADIUS_RANGE = 20000;
    private static final int MIN_RADIUS_RANGE = 100;

    public UserLocationInfo {
        validateUserLocationInfo(x, y, radius);
    }

    private void validateUserLocationInfo(double x, double y, int radius) {
        if (!(validateLongitude(x)
            && validateLatitude(y)
            && validateRadius(radius))) {
            throw new InvalidUserLocationException();
        }
    }

    private static boolean validateRadius(int radius) {
        return MIN_RADIUS_RANGE <= radius && radius <= MAX_RADIUS_RANGE;
    }

    private static boolean validateLongitude(double x) {
        return MIN_LONGITUDE_RANGE <= x && x <= MAX_LONGITUDE_RANGE;
    }

    private static boolean validateLatitude(double y) {
        return MIN_LATITUDE_RANGE <= y && y <= MAX_LATITUDE_RANGE;
    }

}
