package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.spotify;

import com.ifoodtest.ahirata.playlistRecommendation.model.MusicGenre;
import com.ifoodtest.ahirata.playlistRecommendation.model.Playlist;
import com.ifoodtest.ahirata.playlistRecommendation.model.Track;
import com.ifoodtest.ahirata.playlistRecommendation.service.PlaylistService;

public class SpotifyService implements PlaylistService {
    public Playlist getPlaylist(MusicGenre genre) {
        // TODO: Implement getPlaylist
        Playlist playlist = new Playlist();
        playlist.addTrack(new Track(genre.name()));

        return playlist;
    }
}
