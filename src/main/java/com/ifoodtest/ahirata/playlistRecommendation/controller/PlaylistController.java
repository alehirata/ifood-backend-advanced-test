package com.ifoodtest.ahirata.playlistRecommendation.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaylistController {
    
    @RequestMapping("/")
    public ResponseEntity<String> getPlaylist(@RequestParam("city") Optional<String> cityName, @RequestParam("lat") Optional<Double> latitude,
            @RequestParam("long") Optional<Double> longitude, HttpServletResponse response) {
        if (cityName.isPresent() && !cityName.get().isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body("City: " + cityName.get());
        }
        else if (latitude.isPresent() && longitude.isPresent()) {
            StringBuffer sb = new StringBuffer("Latitude: ").append(latitude.get()).append(", Longitude: ").append(longitude.get());
            return ResponseEntity.status(HttpStatus.OK).body(sb.toString());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
}