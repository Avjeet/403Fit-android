package com.ccloudapp.fit403.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dev on 14/8/17.
 */

public class ApiError {

    @SerializedName("status")
    private String mStatus;
    @SerializedName("message")
    private String mMessage;

    public ApiError(){

    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
