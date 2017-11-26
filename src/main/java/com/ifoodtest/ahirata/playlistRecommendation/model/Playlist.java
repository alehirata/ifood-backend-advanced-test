package com.ifoodtest.ahirata.playlistRecommendation.model;

import java.util.ArrayList;

public class Playlist {
    private ArrayList<Track> trackList;

    public Playlist() {
        trackList = new ArrayList<>();
    }

    public ArrayList<Track> getTrackList() {
        return trackList;
    }

    public void addTrack(Track track) {
        trackList.add(track);
    }

    public void delTrack(Track track) {
        trackList.remove(track);
    }

    @Override
    public String toString() {
        /* It could be done using the following code too:
         * ObjectMapper mapper = new ObjectMapper();
         * String jsonStr = mapper.writeValueAsString(this);
         */

        StringBuilder sb = new StringBuilder("{\"playlist\": [");
        for (Track track : trackList) {
            sb.append("\"").append(track.toString()).append("\"").append(",");
        }
        if (!trackList.isEmpty()) {
            sb.setCharAt(sb.length() - 1, ']');
        } else {
            sb.append(']');
        }
        sb.append('}');

        return sb.toString();
    }
}
