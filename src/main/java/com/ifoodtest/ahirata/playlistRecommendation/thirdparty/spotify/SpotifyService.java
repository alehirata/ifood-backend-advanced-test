package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.spotify;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ifoodtest.ahirata.playlistRecommendation.model.MusicGenre;
import com.ifoodtest.ahirata.playlistRecommendation.model.Playlist;
import com.ifoodtest.ahirata.playlistRecommendation.model.Track;
import com.ifoodtest.ahirata.playlistRecommendation.service.PlaylistService;

public class SpotifyService implements PlaylistService {

    RestTemplate restTemplate = new RestTemplate();

    static final String PROTOCOL = "https";

    static final String BASE_URL = "accounts.spotify.com";

    static final String TOKEN_PATH = "/api/token";

    static final String VERSION_PATH = "/v1";

    static final String USERS_PATH = "/users";

    static final String PLAYLISTS_PATH = "/playlists";

    static final String TRACKS_PATH = "/tracks";

    static final String AUTHORIZATION_HEADER = "Authorization ";

    static final String FIELDS_PARAM = "fields";

    static final String FIELDS_DEFAULT_VALUE = "items(track(name))";

    static final String LIMIT_PARAM = "limit";

    static final String LIMIT_DEFAULT_VALUE = "10";

    static String authorization;

    static String token;

    static int tokenExpirationTimestamp;

    static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    HashMap<MusicGenre, String> genreToUserIdMap;

    HashMap<MusicGenre, String> genreToPlaylistIdMap;

    public SpotifyService() {
        try {
            // TODO: Replace it by property file
            String homeDir = System.getProperty("user.home");
            authorization = new String(Files.readAllBytes(Paths.get(homeDir, "keys", "spotify.key"))).trim();

            genreToUserIdMap = new HashMap<>();
            genreToUserIdMap.put(MusicGenre.PARTY, "spotify");
            genreToUserIdMap.put(MusicGenre.POP, "spotify");
            genreToUserIdMap.put(MusicGenre.ROCK, "spotify");
            genreToUserIdMap.put(MusicGenre.CLASSICAL, "spotify");
            
            genreToPlaylistIdMap = new HashMap<>();
            genreToPlaylistIdMap.put(MusicGenre.PARTY, "37i9dQZF1DX2nwuHNKim4S");
            genreToPlaylistIdMap.put(MusicGenre.POP, "37i9dQZF1DWUaThf8nMdW6");
            genreToPlaylistIdMap.put(MusicGenre.ROCK, "37i9dQZF1DWXRqgorJj26U");
            genreToPlaylistIdMap.put(MusicGenre.CLASSICAL, "37i9dQZF1DX8Sz1gsYZdwj");
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTokenIfNeeded() {
        boolean needNewToken = true;
        try {
            rwLock.readLock().lock();
            // TODO: verify expiration time
            if (token != null && !token.isEmpty() && token.length() > 0) {
                needNewToken = false;
            }
        } 
        finally {
            rwLock.readLock().unlock();
        }
        if (needNewToken) {
            requestToken();
        }
    }

    public void requestToken() {
        try {
            rwLock.writeLock().lock();

            // TODO: verify expiration time
            // Double-check
            if (token != null && !token.isEmpty() && token.length() > 0) {
                return;
            }

            UriComponentsBuilder builder = UriComponentsBuilder
                    .fromPath("/")
                    .scheme(PROTOCOL)
                    .host(BASE_URL)
                    .path(TOKEN_PATH);
            String url = builder.build().toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set(AUTHORIZATION_HEADER, "Basic " + authorization);

            TokenRequest tokenRequest = new TokenRequest();
            tokenRequest.setGrant_type("client_credentials");
            HttpEntity<TokenRequest> req = new HttpEntity<TokenRequest>(tokenRequest, headers);

            try {
                ResponseEntity<TokenResponse> resp = restTemplate.postForEntity(url, req.toString(),
                        TokenResponse.class);

                if (resp.getStatusCode() == HttpStatus.OK) {
                    TokenResponse tokenResponse = resp.getBody();
                    // TODO: Replace stdout for log4j
                    System.out.println("HTTP POST - " + url + " - " + tokenResponse.toString());
                    token = tokenResponse.getAccess_token();
                    // TODO: set expiration time
                    return;
                }
            } 
            catch (Exception ex) {
                System.err.println("HTTP POST - " + url);    
            }

            
        }
        finally {
            if (rwLock.isWriteLocked()) {
                rwLock.writeLock().unlock();
            }
        }
    }

    String getPlaylistUrl(MusicGenre genre) {
        String userId = genreToUserIdMap.get(genre);
        String playlistId = genreToPlaylistIdMap.get(genre);
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromPath("/")
                .scheme(PROTOCOL)
                .host(BASE_URL)
                .path(VERSION_PATH)
                .path(USERS_PATH)
                .path(userId)
                .path(PLAYLISTS_PATH)
                .path(playlistId)
                .path(TRACKS_PATH)
                .queryParam(FIELDS_PARAM, FIELDS_DEFAULT_VALUE)
                .queryParam(LIMIT_PARAM, LIMIT_DEFAULT_VALUE);
        return builder.build().toString();
    }

    public Playlist getPlaylist(MusicGenre genre) {
        refreshTokenIfNeeded();
        
        String tokenStr;
        try {
            rwLock.readLock().lock();
            tokenStr = token;
        }
        finally {
            rwLock.readLock().unlock();
        }

        String url = getPlaylistUrl(genre);

        return requestPlaylist(url, tokenStr);
        
    }
    
    Playlist requestPlaylist(String url, String tokenStr) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set(AUTHORIZATION_HEADER, "Bearer " + tokenStr);

        ResponseEntity<TrackListResponse> resp = restTemplate.getForEntity(url, TrackListResponse.class);

        if (resp.getStatusCode() == HttpStatus.OK) {
            TrackListResponse trackListResponse = resp.getBody();
            // TODO: Replace stdout for log4j
            System.out.println("HTTP GET - " + url + " - " + trackListResponse.toString());
            
            return buildPlaylist(trackListResponse.getItems());
        }

        return new Playlist();
    }

    Playlist buildPlaylist(TrackListItem[] items) {
        Playlist playlist = new Playlist();
        if (items == null || items.length == 0) {
            return playlist;
        }

        for (TrackListItem i : items) {
            playlist.addTrack(new Track(i.getTrack().getName()));
        }
        
        return playlist;
    }
}
