package com.ifoodtest.ahirata.playlistRecommendation.service;

import com.ifoodtest.ahirata.playlistRecommendation.model.Weather;

public interface WeatherService {
    public Weather getWeather(String city);
    public Weather getWeather(Double lon, Double lat);
}
