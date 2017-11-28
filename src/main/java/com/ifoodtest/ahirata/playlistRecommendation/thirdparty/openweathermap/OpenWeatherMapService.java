package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.openweathermap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ifoodtest.ahirata.playlistRecommendation.model.Weather;
import com.ifoodtest.ahirata.playlistRecommendation.service.WeatherService;

public class OpenWeatherMapService implements WeatherService {

    RestTemplate restTemplate = new RestTemplate();

    static final String PROTOCOL = "http";

    static final String BASE_URL = "api.openweathermap.org/data/2.5";

    static final String WEATHER_RESOURCE = "/weather";

    static final String CITY_PARAM = "q";

    static final String LONGITUDE_PARAM = "lon";

    static final String LATITUDE_PARAM = "lat";

    static final String UNITS_PARAM = "units";

    static final String CELSIUS_VALUE = "metric";

    static final String APPID_PARAM = "appid";

    String appId;

    public OpenWeatherMapService() {
        try {
            // TODO: Replace it by property file
            String homeDir = System.getProperty("user.home");
            appId = new String(Files.readAllBytes(Paths.get(homeDir, "keys", "owmAppId.key"))).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Weather getWeather(String city) throws Exception{
        return requestWeather(getWeatherUrl(city));
    }

    public Weather getWeather(Double lon, Double lat) throws Exception {
        return requestWeather(getWeatherUrl(lon, lat));
    }

    String getWeatherUrl(String city) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/")
                .scheme(PROTOCOL)
                .host(BASE_URL)
                .path(WEATHER_RESOURCE)
                .queryParam(APPID_PARAM, appId)
                .queryParam(UNITS_PARAM, CELSIUS_VALUE)
                .queryParam(CITY_PARAM, city);
        return builder.build().toString();
    }

    String getWeatherUrl(Double lon, Double lat) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("/")
                .scheme(PROTOCOL)
                .host(BASE_URL)
                .path(WEATHER_RESOURCE)
                .queryParam(APPID_PARAM, appId)
                .queryParam(UNITS_PARAM, CELSIUS_VALUE)
                .queryParam(LONGITUDE_PARAM, lon)
                .queryParam(LATITUDE_PARAM, lat);
        return builder.build().toString();
    }

    Weather requestWeather(String url) throws Exception {
        try {
            ResponseEntity<WeatherResponse> resp = restTemplate.getForEntity(url, WeatherResponse.class);

            if (resp.getStatusCode() == HttpStatus.OK) {
                WeatherResponse weatherResponse = resp.getBody();
                // TODO: Replace stdout for log4j
                System.out.println("HTTP GET - " + url + " - " + weatherResponse.toString());
                Double temperature = weatherResponse.getMain().getTemp();

                return Weather.getWeather(temperature);
            }

            System.err.println("HTTP GET - " + url + " - " + resp.getStatusCode().getReasonPhrase());
            return Weather.getWeather(null);
        }
        catch (Exception ex) {
            System.err.println("HTTP GET - " + url);
            throw ex;
        }
    }
}
