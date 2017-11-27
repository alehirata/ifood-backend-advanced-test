package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.spotify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TrackListResponse {
    private TrackListItem[] items;

    public TrackListItem[] getItems() {
        return items;
    }

    public void setItems(TrackListItem[] items) {
        this.items = items;
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
