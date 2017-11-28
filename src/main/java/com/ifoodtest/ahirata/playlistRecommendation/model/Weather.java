package com.ifoodtest.ahirata.playlistRecommendation.model;

public enum Weather {
    HOT(3),
    WARM(2),
    CHILLY(1),
    FREEZING(0),
    UNKNOWN(-1);

    public final static double HOT_THREASHOLD = 30.0;

    public final static double WARM_THREASHOLD = 15.0;

    public final static double CHILLY_THREASHOLD = 10.0;

    protected int code;

    private Weather(int code) {
        this.code = code;
    }

    public static Weather getWeather(Double temperature) {
        if (temperature == null) {
            return Weather.UNKNOWN;
        }
        if (temperature >= HOT_THREASHOLD) {
            return Weather.HOT;
        }
        if (temperature >= WARM_THREASHOLD) {
            return Weather.WARM;
        }
        if (temperature >= CHILLY_THREASHOLD) {
            return Weather.CHILLY;
        }
        else {
            return Weather.FREEZING;
        }
    }
}