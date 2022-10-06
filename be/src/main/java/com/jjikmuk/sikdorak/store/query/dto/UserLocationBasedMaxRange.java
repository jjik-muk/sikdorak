package com.jjikmuk.sikdorak.store.query.dto;

import com.jjikmuk.sikdorak.store.command.domain.UserLocationInfo;
import lombok.Getter;

@Getter
public class UserLocationBasedMaxRange {

    //meter 당 위도,경도를 구할때 사용합니다.
    private static final double EARTH_RADIUS = 6371;

    private final double maxX;
    private final double maxY;
    private final double minX;
    private final double minY;

    //참고 : https://wildeveloperetrain.tistory.com/171
    public UserLocationBasedMaxRange(UserLocationInfo locationInfo) {
        double meterForLatitude = (1 / Math.toRadians(EARTH_RADIUS)) / 1000;
        double meterForLongitude = (1 / (Math.toRadians(EARTH_RADIUS) * Math.cos(Math.toRadians(
            locationInfo.y())))) / 1000;

        this.maxX = calculateMaxPoint(locationInfo.x(),
            locationInfo.radius(), meterForLongitude);
        this.maxY = calculateMaxPoint(locationInfo.y(),
            locationInfo.radius(), meterForLatitude);
        this.minX = calculateMinPoint(locationInfo.x(),
            locationInfo.radius(), meterForLongitude);
        this.minY = calculateMinPoint(locationInfo.y(),
            locationInfo.radius(), meterForLatitude);
    }

    private double calculateMaxPoint(double point, int radius, double meterForPoint) {
        return point + (radius * meterForPoint);
    }

    private double calculateMinPoint(double point, int radius, double meterForPoint) {
        return point - (radius * meterForPoint);
    }
}
