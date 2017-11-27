package com.ifoodtest.ahirata.playlistRecommendation.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class WeatherTest {

    @Test
    public void unknownWeatherTest() {
        assertEquals(Weather.UNKNOWN, Weather.getWeather(null));
    }

    @Test
    public void hotWeatherTest() {
        assertEquals(Weather.HOT, Weather.getWeather(Double.POSITIVE_INFINITY));
        assertEquals(Weather.HOT, Weather.getWeather(Double.MAX_VALUE));
        assertEquals(Weather.HOT, Weather.getWeather(30.1));
        assertEquals(Weather.HOT, Weather.getWeather(30.0));

        assertNotEquals(Weather.HOT, Weather.getWeather(29.999999));
        assertNotEquals(Weather.HOT, Weather.getWeather(29.0));
        assertNotEquals(Weather.HOT, Weather.getWeather(25.0));
        assertNotEquals(Weather.HOT, Weather.getWeather(20.0));
        assertNotEquals(Weather.HOT, Weather.getWeather(15.0));
        assertNotEquals(Weather.HOT, Weather.getWeather(12.5));
        assertNotEquals(Weather.HOT, Weather.getWeather(10.0));
        assertNotEquals(Weather.HOT, Weather.getWeather(5.0));
        assertNotEquals(Weather.HOT, Weather.getWeather(0.0));
        assertNotEquals(Weather.HOT, Weather.getWeather(-1.0));
        assertNotEquals(Weather.HOT, Weather.getWeather(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void warmWeatherTest() {
        assertNotEquals(Weather.WARM, Weather.getWeather(Double.POSITIVE_INFINITY));
        assertNotEquals(Weather.WARM, Weather.getWeather(Double.MAX_VALUE));
        assertNotEquals(Weather.WARM, Weather.getWeather(30.1));
        assertNotEquals(Weather.WARM, Weather.getWeather(30.0));

        assertEquals(Weather.WARM, Weather.getWeather(29.999999));
        assertEquals(Weather.WARM, Weather.getWeather(29.0));
        assertEquals(Weather.WARM, Weather.getWeather(25.0));
        assertEquals(Weather.WARM, Weather.getWeather(20.0));
        assertEquals(Weather.WARM, Weather.getWeather(15.1));
        assertEquals(Weather.WARM, Weather.getWeather(15.0));

        assertNotEquals(Weather.WARM, Weather.getWeather(14.999999));
        assertNotEquals(Weather.WARM, Weather.getWeather(12.5));
        assertNotEquals(Weather.WARM, Weather.getWeather(10.0));
        assertNotEquals(Weather.WARM, Weather.getWeather(5.0));
        assertNotEquals(Weather.WARM, Weather.getWeather(0.0));
        assertNotEquals(Weather.WARM, Weather.getWeather(-1.0));
        assertNotEquals(Weather.WARM, Weather.getWeather(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void chillyWeatherTest() {
        assertNotEquals(Weather.CHILLY, Weather.getWeather(Double.POSITIVE_INFINITY));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(Double.MAX_VALUE));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(30.1));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(30.0));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(29.999999));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(29.0));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(25.0));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(20.0));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(15.1));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(15.0));

        assertEquals(Weather.CHILLY, Weather.getWeather(14.999999));
        assertEquals(Weather.CHILLY, Weather.getWeather(12.5));
        assertEquals(Weather.CHILLY, Weather.getWeather(10.1));
        assertEquals(Weather.CHILLY, Weather.getWeather(10.0));

        assertNotEquals(Weather.CHILLY, Weather.getWeather(9.99999));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(5.0));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(0.0));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(-1.0));
        assertNotEquals(Weather.CHILLY, Weather.getWeather(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void freezingWeatherTest() {
        assertNotEquals(Weather.FREEZING, Weather.getWeather(Double.POSITIVE_INFINITY));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(Double.MAX_VALUE));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(30.1));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(30.0));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(29.999999));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(29.0));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(25.0));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(20.0));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(15.1));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(15.0));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(14.999999));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(12.5));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(10.1));
        assertNotEquals(Weather.FREEZING, Weather.getWeather(10.0));

        assertEquals(Weather.FREEZING, Weather.getWeather(9.99999));
        assertEquals(Weather.FREEZING, Weather.getWeather(5.0));
        assertEquals(Weather.FREEZING, Weather.getWeather(0.0));
        assertEquals(Weather.FREEZING, Weather.getWeather(-1.0));
        assertEquals(Weather.FREEZING, Weather.getWeather(Double.NEGATIVE_INFINITY));
    }
}
