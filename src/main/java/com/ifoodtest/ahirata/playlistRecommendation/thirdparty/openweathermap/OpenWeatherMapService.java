package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.openweathermap;

import com.ifoodtest.ahirata.playlistRecommendation.model.Weather;
import com.ifoodtest.ahirata.playlistRecommendation.service.WeatherService;

public class OpenWeatherMapService implements WeatherService {
    public Weather getWeather(String city) {
        // TODO: Implement getWeather(String city)
        return Weather.CHILLY;
    }

    public Weather getWeather(Double lon, Double lat) {
        // TODO: Implement getWeather(Double lon, Double lat)
        return Weather.FREEZING;
    }
}
