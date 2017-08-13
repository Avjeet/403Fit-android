package com.ccloudapp.fit403.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dev on 12/8/17.
 */

public class AuthResponse {
    @SerializedName("status")
    private String mStatus;
    @SerializedName("loggedin_url")
    private String mLoggedInUrl;
    @SerializedName("access_token")
    private String mAccessToken;
    @SerializedName("message")
    private String mMessage;

    public AuthResponse(){
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
    }

    public String getLoggedInUrl() {
        return mLoggedInUrl;
    }

    public void setLoggedInUrl(String loggedInUrl) {
        mLoggedInUrl = loggedInUrl;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @Override
    public String toString() {
        return "Status: "+mStatus+"\n";
    }
}
