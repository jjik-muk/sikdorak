package com.jjikmuk.sikdorak.common.util;

public class DistanceCalcUtil {

    private static final double MILES_TO_METER = 60 * 1.1515 * 1609.344;

    public static boolean isValidDistance(double desX, double desY, double curX, double curY, int radius) {
        double distance = DistanceCalcUtil.calculateDistanceInMeter(desX, desY, curX, curY);
        return radius > distance;
    }

    private static double calculateDistanceInMeter(double desX, double desY, double curX, double curY) {
        double distance = Math.sin(Math.toRadians(desY)) * Math.sin(Math.toRadians(curY)) +
            Math.cos(Math.toRadians(desY)) * Math.cos(Math.toRadians(curY)) *
                Math.cos(Math.toRadians(desX - curX));
        distance = Math.toDegrees(Math.acos(distance));
        return distance * MILES_TO_METER;
    }

}
