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
        double mForLatitude = (1 /Math.toRadians(EARTH_RADIUS)) / 1000;
        double mForLongitude = (1 / (Math.toRadians(EARTH_RADIUS) * Math.cos(Math.toRadians(
            userLocationInfoRequest.getY())))) / 1000;

        this.maxX = calculateMaxPoint(userLocationInfoRequest.getX(), userLocationInfoRequest.getRadius(), mForLongitude);
        this.maxY = calculateMaxPoint(userLocationInfoRequest.getY(), userLocationInfoRequest.getRadius(), mForLatitude);
        this.minX = calculateMinPoint(userLocationInfoRequest.getX(), userLocationInfoRequest.getRadius(), mForLongitude);
        this.minY = calculateMinPoint(userLocationInfoRequest.getY(), userLocationInfoRequest.getRadius(), mForLatitude);
    }

    private double calculateMaxPoint(double point, int radius, double meterForPoint) {
        return point + (radius * meterForPoint);
    }

    private double calculateMinPoint(double point, int radius, double meterForPoint) {
        return point - (radius * meterForPoint);
    }
}
