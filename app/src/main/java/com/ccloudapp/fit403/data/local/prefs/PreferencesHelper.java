package com.ccloudapp.fit403.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.ccloudapp.fit403.di.context.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by amit on 30/11/16.
 */
@Singleton
public class PreferencesHelper {

    public static final String PREF_FILE_NAME = "fit_app_pref_file";

    // These names are the prefixes;
    private static final String PREFIX_PREF_FIT_NAME = "fit_name_";
    private static final String PREFIX_PREF_FIT_IMAGE_URL = "fit_image_url_";
    private static final String PREF_ACTIVE_ACCOUNT_TOKEN = "chosen_account";
    private static final String PREFIX_PREF_FIT_EMAIL = "fit_email";
    private static final String PREFIX_PREF_FIT_GENDER = "fit_gender";
    private static final String PREFIX_PREF_FIT_DOB = "fit_dob";
    private static final String PREFIX_PREF_FIT_SUBJECT = "fit_subject";
    private static final String PREFIX_PREF_FIT_DESCRIPTION = "fit_description";
    private static final String PREFIX_PREF_FIT_WORKOUT_TYPE = "fit_workout_type";
    private static final String PREFIX_PREF_FIT_WORKOUT_STYLE = "fit_workout_style";
    private static final String PREFIX_PREF_FIT_AREA_OF_FOCUS = "fit_area_focus";
    private static final String PREFIX_PREF_FIT_LOCATION_ADDRESS = "dot_address";
    private static final String PREFIX_PREF_FIT_LOCATION_LATLONG = "dot_lat_long";


    private final SharedPreferences mPref;

    @Inject
    public PreferencesHelper(@ApplicationContext Context context) {
        mPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
    }

    public void clear() {
        mPref.edit().clear().apply();
    }

    public boolean hasActiveAccount() {
        return !TextUtils.isEmpty(getActiveAccountToken());
    }

    public String getActiveAccountToken() {
        return mPref.getString(PREF_ACTIVE_ACCOUNT_TOKEN, null);
    }

    // Set activeAccountToken
    public void setActiveAccountToken(final String accountToken) {
        mPref.edit().putString(PREF_ACTIVE_ACCOUNT_TOKEN, accountToken).apply();
    }

    // Set username
    public void setName(final String name) {
        mPref.edit().putString(PREFIX_PREF_FIT_NAME, name).apply();
    }

    // Returns username
    public String getName() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_NAME, null) : null;
    }

    public void setImageUrl(final String imageUrl) {
        mPref.edit().putString(PREFIX_PREF_FIT_IMAGE_URL, imageUrl).apply();
    }

    public String getImageUrl() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_IMAGE_URL, null) : null;
    }

    public void setEmail(final String email) {
        mPref.edit().putString(PREFIX_PREF_FIT_EMAIL, email).apply();
    }

    public String getEmail() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_EMAIL, null) : null;
    }

    public void setDob(final String dob) {
        mPref.edit().putString(PREFIX_PREF_FIT_DOB, dob).apply();
    }

    public String getDob() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_DOB, null) : null;
    }
    public void setSubject(final String subject) {
        mPref.edit().putString(PREFIX_PREF_FIT_SUBJECT, subject).apply();
    }

    public String getSubject() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_SUBJECT, null) : null;
    }
    public void setDescription(final String description) {
        mPref.edit().putString(PREFIX_PREF_FIT_DESCRIPTION, description).apply();
    }

    public String getDescription() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_DESCRIPTION, null) : null;
    }
    public void setWorkoutType(final String workoutType) {
        mPref.edit().putString(PREFIX_PREF_FIT_WORKOUT_TYPE, workoutType).apply();
    }

    public String getWorkoutType() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_WORKOUT_TYPE, null) : null;
    }
    public void setWorkoutStyle(final String workoutStyle) {
        mPref.edit().putString(PREFIX_PREF_FIT_WORKOUT_STYLE, workoutStyle).apply();
    }

    public String getWorkoutStyle() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_WORKOUT_STYLE, null) : null;
    }
    public void setAreaOfFocus(final String areaOfFocus) {
        mPref.edit().putString(PREFIX_PREF_FIT_AREA_OF_FOCUS, areaOfFocus).apply();
    }

    public String getAreaOfFocus() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_FIT_AREA_OF_FOCUS, null) : null;
    }

    public void setGender(final String gender) {
        mPref.edit().putString(PREFIX_PREF_FIT_GENDER, gender).apply();
    }

    public boolean hasProfileFilled() {
        return !TextUtils.isEmpty(getName());
    }

    public void setAddress(String address){
        mPref.edit().putString(PREFIX_PREF_FIT_LOCATION_ADDRESS, address).apply();
    }

    public String getAddress(){
        return mPref.getString(PREFIX_PREF_FIT_LOCATION_ADDRESS, null);
    }

    public void setLatLong(String latLong){
        mPref.edit().putString(PREFIX_PREF_FIT_LOCATION_LATLONG, latLong).apply();
    }

    public String latLong(){
        return mPref.getString(PREFIX_PREF_FIT_LOCATION_LATLONG, null);
    }

}
