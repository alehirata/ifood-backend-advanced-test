package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.openweathermap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherResponse {

    private int id;

    private String name;

    private int cod;

    private WeatherCoordinates coord;

    private WeatherMain main;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public WeatherCoordinates getCoord() {
        return coord;
    }

    public void setCoord(WeatherCoordinates coord) {
        this.coord = coord;
    }

    public WeatherMain getMain() {
        return main;
    }

    public void setMain(WeatherMain main) {
        this.main = main;
    }

    @Override
    public String toString() {
        String jsonStr = null;
        ObjectMapper mapper = new ObjectMapper();

        try {
            jsonStr = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonStr;
    }
}
