package com.ifoodtest.ahirata.playlistRecommendation.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.ifoodtest.ahirata.playlistRecommendation.model.MusicGenre;
import com.ifoodtest.ahirata.playlistRecommendation.model.Playlist;
import com.ifoodtest.ahirata.playlistRecommendation.model.Track;
import com.ifoodtest.ahirata.playlistRecommendation.model.Weather;

public class PlaylistRecommendationServiceTest {
    @Mock
    private WeatherService mockWeatherService = mock(WeatherService.class);

    @Mock
    private PlaylistService mockPlaylistService = mock(PlaylistService.class);

    private PlaylistRecommendationService playlistRecommendation;

    @Before
    public void setup() {
        playlistRecommendation = new PlaylistRecommendationService(mockWeatherService, mockPlaylistService);
        try {
            mockWeatherService();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        mockPlaylistService();
    }

    void mockWeatherService() throws Exception {
        when(mockWeatherService.getWeather("Belem")).thenReturn(Weather.HOT);
        when(mockWeatherService.getWeather("Campinas")).thenReturn(Weather.WARM);
        when(mockWeatherService.getWeather("Tokyo")).thenReturn(Weather.CHILLY);
        when(mockWeatherService.getWeather("London")).thenReturn(Weather.FREEZING);
        when(mockWeatherService.getWeather(-48.5, -1.46)).thenReturn(Weather.HOT);
        when(mockWeatherService.getWeather(-47.06, -22.91)).thenReturn(Weather.WARM);
        when(mockWeatherService.getWeather(139.69, 35.69)).thenReturn(Weather.CHILLY);
        when(mockWeatherService.getWeather(-0.13, 51.51)).thenReturn(Weather.FREEZING);
    }

    Playlist createPartyPlaylist() {
        Playlist playlist = new Playlist();
        playlist.addTrack(new Track("Chandelier"));
        playlist.addTrack(new Track("One Last Time"));
        playlist.addTrack(new Track("Closer"));
        return playlist;
    }

    Playlist createPopPlaylist() {
        Playlist playlist = new Playlist();
        playlist.addTrack(new Track("Where Is The Love?"));
        playlist.addTrack(new Track("One Step At a Time"));
        playlist.addTrack(new Track("Complicated"));
        return playlist;
    }

    Playlist createRockPlaylist() {
        Playlist playlist = new Playlist();
        playlist.addTrack(new Track("We Will Rock You"));
        playlist.addTrack(new Track("Paradise City"));
        playlist.addTrack(new Track("House Of The Rising Sun"));
        return playlist;
    }

    Playlist createClassicalPlaylist() {
        Playlist playlist = new Playlist();
        playlist.addTrack(new Track("Wiegenlied, Op.49, No.4"));
        playlist.addTrack(new Track("Tchaikovsky: Swan Lake (Ballet), Op. 20, Act 2: No. 10, Scène (Moderato)"));
        playlist.addTrack(new Track("Les berceaux, Op.23, No.1"));
        return playlist;
    }

    boolean isTrackInPlaylist(Track track, Playlist playlist) {
        boolean found = false;
        for (Track t : playlist.getTrackList()) {
            if (track.getName().equals(t.getName())) {
                found = true;
                break;
            }
        }
        return found;

    }

    boolean arePlaylistsEqual(Playlist p1, Playlist p2) {
        if (p1.getTrackList().size() != p2.getTrackList().size()) {
            return false;
        }

        for (Track t1 : p1.getTrackList()) {
            if (!isTrackInPlaylist(t1, p2)) {
                return false;
            }
        }

        for (Track t2 : p2.getTrackList()) {
            if (!isTrackInPlaylist(t2, p1)) {
                return false;
            }
        }

        return true;
    }

    void mockPlaylistService() {
        when(mockPlaylistService.getPlaylist(MusicGenre.PARTY)).thenReturn(createPartyPlaylist());
        when(mockPlaylistService.getPlaylist(MusicGenre.POP)).thenReturn(createPopPlaylist());
        when(mockPlaylistService.getPlaylist(MusicGenre.ROCK)).thenReturn(createRockPlaylist());
        when(mockPlaylistService.getPlaylist(MusicGenre.CLASSICAL)).thenReturn(createClassicalPlaylist());
    }

    @Test
    public void getGenreByWeatherTest() {
        assertEquals(MusicGenre.PARTY, playlistRecommendation.getGenre(Weather.HOT));
        assertEquals(MusicGenre.POP, playlistRecommendation.getGenre(Weather.WARM));
        assertEquals(MusicGenre.ROCK, playlistRecommendation.getGenre(Weather.CHILLY));
        assertEquals(MusicGenre.CLASSICAL, playlistRecommendation.getGenre(Weather.FREEZING));
    }

    @Test
    public void getPartyPlaylistTest() throws Exception {
        Playlist partyPlaylist = createPartyPlaylist();
        assertTrue(arePlaylistsEqual(partyPlaylist, playlistRecommendation.getPlaylist("Belem")));
        assertTrue(arePlaylistsEqual(partyPlaylist, playlistRecommendation.getPlaylist(-48.5, -1.46)));

        partyPlaylist.addTrack(new Track(" "));
        assertFalse(arePlaylistsEqual(partyPlaylist, playlistRecommendation.getPlaylist("Belem")));
        assertFalse(arePlaylistsEqual(partyPlaylist, playlistRecommendation.getPlaylist(-48.5, -1.46)));
    }

    @Test
    public void getPopPlaylistTest() throws Exception {
        Playlist popPlaylist = createPopPlaylist();
        assertTrue(arePlaylistsEqual(popPlaylist, playlistRecommendation.getPlaylist("Campinas")));
        assertTrue(arePlaylistsEqual(popPlaylist, playlistRecommendation.getPlaylist(-47.06, -22.91)));

        popPlaylist.addTrack(new Track(" "));
        assertFalse(arePlaylistsEqual(popPlaylist, playlistRecommendation.getPlaylist("Campinas")));
        assertFalse(arePlaylistsEqual(popPlaylist, playlistRecommendation.getPlaylist(-47.06, -22.91)));
    }

    @Test
    public void getRockPlaylistTest() throws Exception {
        Playlist rockPlaylist = createRockPlaylist();
        assertTrue(arePlaylistsEqual(rockPlaylist, playlistRecommendation.getPlaylist("Tokyo")));
        assertTrue(arePlaylistsEqual(rockPlaylist, playlistRecommendation.getPlaylist(139.69, 35.69)));

        rockPlaylist.addTrack(new Track(" "));
        assertFalse(arePlaylistsEqual(rockPlaylist, playlistRecommendation.getPlaylist("Tokyo")));
        assertFalse(arePlaylistsEqual(rockPlaylist, playlistRecommendation.getPlaylist(139.69, 35.69)));
    }

    @Test
    public void getClassicalPlaylistTest() throws Exception {
        Playlist classicalPlaylist = createClassicalPlaylist();
        assertTrue(arePlaylistsEqual(classicalPlaylist, playlistRecommendation.getPlaylist("London")));
        assertTrue(arePlaylistsEqual(classicalPlaylist, playlistRecommendation.getPlaylist(-0.13, 51.51)));

        classicalPlaylist.addTrack(new Track(" "));
        assertFalse(arePlaylistsEqual(classicalPlaylist, playlistRecommendation.getPlaylist("London")));
        assertFalse(arePlaylistsEqual(classicalPlaylist, playlistRecommendation.getPlaylist(-0.13, 51.51)));
    }
}
