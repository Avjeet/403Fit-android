package com.ccloudapp.fit403.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by AVJEET on 03-09-2017.
 */

public class Exercise_category {

    @SerializedName("id")
    private String id;

    @SerializedName("exercise_title")
    private String exercise_title;

    @SerializedName("image_url")
    private String img_url;

    public String getExercise_title() {
        return exercise_title;
    }

    public void setExercise_title(String exercise_title) {
        this.exercise_title = exercise_title;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
