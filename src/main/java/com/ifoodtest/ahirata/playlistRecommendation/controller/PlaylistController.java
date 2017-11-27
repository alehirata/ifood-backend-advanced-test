package com.ifoodtest.ahirata.playlistRecommendation.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifoodtest.ahirata.playlistRecommendation.model.Playlist;
import com.ifoodtest.ahirata.playlistRecommendation.service.PlaylistRecommendationService;
import com.ifoodtest.ahirata.playlistRecommendation.thirdparty.openweathermap.OpenWeatherMapService;
import com.ifoodtest.ahirata.playlistRecommendation.thirdparty.spotify.SpotifyService;

@RestController
public class PlaylistController {

    PlaylistRecommendationService playlistReommendation = new PlaylistRecommendationService(new OpenWeatherMapService(),
            new SpotifyService());

    @RequestMapping("/")
    public ResponseEntity<String> getPlaylist(@RequestParam("city") Optional<String> cityName,
            @RequestParam("lat") Optional<Double> longitude, @RequestParam("long") Optional<Double> latitude,
            HttpServletResponse response) {

        try {
            if (cityName.isPresent() && !cityName.get().isEmpty()) {
                Playlist playlist = playlistReommendation.getPlaylist(cityName.get());
                return ResponseEntity.status(HttpStatus.OK).body(playlist.toString());
            } else if (longitude.isPresent() && latitude.isPresent()) {
                Playlist playlist = playlistReommendation.getPlaylist(longitude.get(), latitude.get());
                return ResponseEntity.status(HttpStatus.OK).body(playlist.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}