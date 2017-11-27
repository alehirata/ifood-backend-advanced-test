package com.ifoodtest.ahirata.playlistRecommendation.thirdparty.spotify;

public class TokenRequest {

    private String grant_type;

    //static final String DEFAULT_GRANT_TYPE = "client_credentials";
/*
    public TokenRequest() {
        //this.grant_type = DEFAULT_GRANT_TYPE;
        "client_credentials"
    }
*/
    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
