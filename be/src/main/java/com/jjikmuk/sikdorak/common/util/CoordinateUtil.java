package com.jjikmuk.sikdorak.common.util;

import com.jjikmuk.sikdorak.store.controller.request.UserLocationInfo;
import com.jjikmuk.sikdorak.store.service.dto.SquareCoordinates;
import org.springframework.stereotype.Component;

@Component
public class CoordinateUtil {

    private static final double MAX_LATITUDE_RANGE = 90.0;
    private static final double MIN_LATITUDE_RANGE = -90.0;
    private static final double MAX_LONGITUDE_RANGE = 180.0;
    private static final double MIN_LONGITUDE_RANGE = -180.0;
    private static final int MAX_RADIUS_RANGE = 20000;
    private static final int MIN_RADIUS_RANGE = 100;
    private static final double MILES_TO_METER = 60 * 1.1515 * 1609.344;

    //KM 단위
    private static final int EARTH_RADIUS = 3761;

    public SquareCoordinates calculateMaxMinCoordinates(UserLocationInfo userLocationInfo) {
        double mForLatitude = (1 /Math.toRadians(EARTH_RADIUS)) / 1000;
        double mForLongitude = (1 / (Math.toRadians(EARTH_RADIUS) * Math.cos(Math.toRadians(
            userLocationInfo.y())))) / 1000;

        double maxX = userLocationInfo.x() + (userLocationInfo.radius() * mForLongitude);
        double maxY = userLocationInfo.y() + (userLocationInfo.radius() * mForLatitude);
        double minX = userLocationInfo.x() - (userLocationInfo.radius() * mForLongitude);
        double minY = userLocationInfo.y() - (userLocationInfo.radius() * mForLatitude);

        return new SquareCoordinates(maxX, maxY, minX, minY);
    }

    public boolean isValidDistance(double desX, double desY, UserLocationInfo userLocationInfo) {
        double distance = calculateDistanceInMeter(desX, desY, userLocationInfo.x(), userLocationInfo.y());
        return userLocationInfo.radius() > distance;
    }

    public boolean validateRadius(int radius) {
        return MIN_RADIUS_RANGE <= radius && radius <= MAX_RADIUS_RANGE;
    }

    public boolean validateLongitude(double x) {
        return MIN_LONGITUDE_RANGE <= x && x <= MAX_LONGITUDE_RANGE;
    }

    public boolean validateLatitude(double x) {
        return MIN_LATITUDE_RANGE <= x && x <= MAX_LATITUDE_RANGE;
    }

    private double calculateDistanceInMeter(double desX, double desY, double curX, double curY) {
        double distance = Math.sin(Math.toRadians(desY)) * Math.sin(Math.toRadians(curY)) +
            Math.cos(Math.toRadians(desY)) * Math.cos(Math.toRadians(curY)) *
                Math.cos(Math.toRadians(desX - curX));
        distance = Math.toDegrees(Math.acos(distance));
        return distance * MILES_TO_METER;
    }

}
