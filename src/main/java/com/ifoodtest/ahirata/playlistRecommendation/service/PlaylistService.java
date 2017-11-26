package com.ifoodtest.ahirata.playlistRecommendation.service;

import com.ifoodtest.ahirata.playlistRecommendation.model.MusicGenre;
import com.ifoodtest.ahirata.playlistRecommendation.model.Playlist;

public interface PlaylistService {
    public Playlist getPlaylist(MusicGenre genre);
}
