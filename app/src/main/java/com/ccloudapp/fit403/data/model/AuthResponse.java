package com.ccloudapp.fit403.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dev on 12/8/17.
 */

public class AuthResponse {
    @SerializedName("status")
    private String mStatus;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("token")
    private String mAccessToken;

    public AuthResponse(){
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    @Override
    public String toString() {
        return "Status: "+mStatus+"\n";
    }
}
