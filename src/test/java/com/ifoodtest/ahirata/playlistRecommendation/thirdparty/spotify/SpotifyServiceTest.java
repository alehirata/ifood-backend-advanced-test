package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.spotify;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SpotifyServiceTest {

    private SpotifyService spotifyService = new SpotifyService();;

    private final static String REQUEST_TOKEN_URL = "https://accounts.spotify.com/api/token";

    private final static String GET_PLAYLIST_STRFMT = "https://api.spotify.com/v1/users/%s/playlists/%s/tracks?fields=items(track(name))&limit=10";

    String formatPlaylistUrl(String userId, String playlistId) {
        return String.format(GET_PLAYLIST_STRFMT, userId, playlistId);
    }

    @Test
    public void getRequestTokenAuthHeaderValueTest() {
        String auth = "abcdeABCDE12345:09876FGHIJklmno";
        String auth64 = "YWJjZGVBQkNERTEyMzQ1OjA5ODc2RkdISUprbG1ubw==";
        assertEquals("Basic " + auth64, spotifyService.getReqTokenAuthHeaderValue(auth));
    }

    @Test
    public void getTokenRequestUrlTest() {
        assertEquals(REQUEST_TOKEN_URL, spotifyService.getTokenRequestUrl());
    }

    @Test
    public void getPlaylistUrlTest() {
        String temp = "https://api.spotify.com/v1/users/{user_id}/playlists/{playlist_id}/tracks?fields=items(track(name))&limit=10";
        assertEquals(temp, formatPlaylistUrl("{user_id}", "{playlist_id}"));
        String test = spotifyService.getPlaylistUrl("{user_id}", "{playlist_id}");
        assertEquals(temp, test);
    }
}
