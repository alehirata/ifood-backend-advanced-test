package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.openweathermap;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class OpenWeatherMapServiceTest {

    private OpenWeatherMapService owmService;

    private final static String URL = "http://api.openweathermap.org/data/2.5/weather?appid=APPID_PLACEHOLDER&units=metric";

    @Before
    public void setup() {
        owmService = new OpenWeatherMapService();
        owmService.appId = "APPID_PLACEHOLDER";
    }

    @Test
    public void getWeatherByCityUrlTest() {
        assertEquals(URL + "&q=London", owmService.getWeatherUrl("London"));
        assertEquals(URL + "&q=Campinas,br", owmService.getWeatherUrl("Campinas,br"));
        assertEquals(URL + "&q=Tokyo", owmService.getWeatherUrl("Tokyo"));
    }

    @Test
    public void getWeatherByLonLatUrlTest() {
        assertEquals(URL + "&lon=1.0&lat=0.0", owmService.getWeatherUrl(1.0, 0.0));
        assertEquals(URL + "&lon=35.0&lat=-47.0", owmService.getWeatherUrl(35.0, -47.0));
        assertEquals(URL + "&lon=-10.0&lat=100.0", owmService.getWeatherUrl(-10.0, 100.0));
        assertEquals(URL + "&lon=-11.0&lat=-11.0", owmService.getWeatherUrl(-11.0, -11.0));
    }
}
