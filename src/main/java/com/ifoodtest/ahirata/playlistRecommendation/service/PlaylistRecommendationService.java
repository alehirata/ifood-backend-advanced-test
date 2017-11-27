package com.ifoodtest.ahirata.playlistRecommendation.service;

import com.ifoodtest.ahirata.playlistRecommendation.model.MusicGenre;
import com.ifoodtest.ahirata.playlistRecommendation.model.Playlist;
import com.ifoodtest.ahirata.playlistRecommendation.model.Weather;

public class PlaylistRecommendationService {
    private WeatherService weatherService;

    private PlaylistService playlistService;
    
    public PlaylistRecommendationService(WeatherService weatherService, PlaylistService playlistService) {
        this.weatherService = weatherService;
        this.playlistService = playlistService;
    }

    public Playlist getPlaylist(String city) throws Exception {
        Weather weather = weatherService.getWeather(city);
        return getPlaylist(weather);
    }

    public Playlist getPlaylist(Double lon, Double lat) throws Exception {
        Weather weather = weatherService.getWeather(lon, lat);
        return getPlaylist(weather);
    }

    Playlist getPlaylist(Weather weather) {
        MusicGenre genre = getGenre(weather);
        return playlistService.getPlaylist(genre);
    }

    MusicGenre getGenre(Weather weather) {
        switch (weather) {
        case HOT:
            return MusicGenre.PARTY;

        case WARM:
            return MusicGenre.POP;

        case CHILLY:
            return MusicGenre.ROCK;

        case FREEZING:
            return MusicGenre.CLASSICAL;
        }

        return null;
    }
}
