package com.jjikmuk.sikdorak.store.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.jjikmuk.sikdorak.store.exception.InvalidUserLocationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserLocationInfoRequest{

    private static final double MAX_LATITUDE_RANGE = 90.0;
    private static final double MIN_LATITUDE_RANGE = -90.0;
    private static final double MAX_LONGITUDE_RANGE = 180.0;
    private static final double MIN_LONGITUDE_RANGE = -180.0;
    private static final int MAX_RADIUS_RANGE = 20000;
    private static final int MIN_RADIUS_RANGE = 100;

    @NotNull
    @Min(-180)
    @Max(180)
    double x;

    @NotNull
    @Min(-90)
    @Max(90)
    double y;

    @NotNull
    @Min(0)
    @Max(20000)
    int radius;

    @JsonCreator
    public UserLocationInfoRequest(double x, double y, int radius) {
        validateUserLocationInfo(x, y, radius);
        this.x = x;
        this.y = y;
        this.radius = radius;
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

    private static  boolean validateLongitude(double x) {
        return MIN_LONGITUDE_RANGE <= x && x <= MAX_LONGITUDE_RANGE;
    }

    private static  boolean validateLatitude(double y) {
        return MIN_LATITUDE_RANGE <= y && y <= MAX_LATITUDE_RANGE;
    }
}
