package com.dawnbit.common.utils;

public class GeolocationUtils {
    static final double EARTH_RADIUS = 6378137.0;

    /**
     * @param x
     * @return
     */
    public static double radians(double x) {
        return x * Math.PI / 180;
    }

    /**
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @param distanceUnit
     * @return
     */
    public static double distanceBetweenPlaces(double lat1, double lng1, double lat2, double lng2, DistanceUnit distanceUnit) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return (EARTH_RADIUS * c) / distanceUnit.conversionFactor;
    }

    /**
     * @param location1
     * @param location2
     * @param distanceUnit
     * @return
     */
    public static double distanceBetweenPlaces(Coordinate location1, Coordinate location2, DistanceUnit distanceUnit) {
        return distanceBetweenPlaces(location1.getLatitude(), location1.getLongitude(), location2.getLatitude(), location2.getLongitude(),
                distanceUnit);
    }

    public enum DistanceUnit {
        KM(1000.0), MILES(1609.344);

        private final double conversionFactor;

        private DistanceUnit(double conversionFactor) {
            this.conversionFactor = conversionFactor;
        }

        public double conversionFactor() {
            return this.conversionFactor;
        }

    }

    public static class Coordinate {
        private double latitude;
        private double longitude;

        public Coordinate() {
        }

        public Coordinate(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        /**
         * @return the latitude
         */
        public double getLatitude() {
            return latitude;
        }

        /**
         * @param latitude the latitude to set
         */
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        /**
         * @return the longitude
         */
        public double getLongitude() {
            return longitude;
        }

        /**
         * @param longitude the longitude to set
         */
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
