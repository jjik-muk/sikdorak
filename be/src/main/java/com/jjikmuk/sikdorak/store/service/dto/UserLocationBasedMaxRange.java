package com.jjikmuk.sikdorak.store.service.dto;

import com.jjikmuk.sikdorak.store.controller.request.UserLocationInfoRequest;
import lombok.Getter;

@Getter
public class UserLocationBasedMaxRange {

    private static final int EARTH_RADIUS = 3761;

    private final double maxX;
    private final double maxY;
    private final double minX;
    private final double minY;

    public UserLocationBasedMaxRange(UserLocationInfoRequest userLocationInfoRequest) {
        double meterForLatitude = (1 / Math.toRadians(EARTH_RADIUS)) / 1000;
        double meterForLongitude = (1 / (Math.toRadians(EARTH_RADIUS) * Math.cos(Math.toRadians(
            userLocationInfoRequest.getY())))) / 1000;

        this.maxX = calculateMaxPoint(userLocationInfoRequest.getX(),
            userLocationInfoRequest.getRadius(), meterForLongitude);
        this.maxY = calculateMaxPoint(userLocationInfoRequest.getY(),
            userLocationInfoRequest.getRadius(), meterForLatitude);
        this.minX = calculateMinPoint(userLocationInfoRequest.getX(),
            userLocationInfoRequest.getRadius(), meterForLongitude);
        this.minY = calculateMinPoint(userLocationInfoRequest.getY(),
            userLocationInfoRequest.getRadius(), meterForLatitude);
    }

    private double calculateMaxPoint(double point, int radius, double meterForPoint) {
        return point + (radius * meterForPoint);
    }

    private double calculateMinPoint(double point, int radius, double meterForPoint) {
        return point - (radius * meterForPoint);
    }
}
