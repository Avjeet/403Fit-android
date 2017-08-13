package com.ccloudapp.fit403.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dev on 12/8/17.
 */

public class AuthRequest {
    @SerializedName("Username")
    private String mUsername;
    @SerializedName("Password")
    private String mPassword;

    public AuthRequest() {
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
