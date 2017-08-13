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

    public static final String PREF_FILE_NAME = "dot_app_pref_file";

    // These names are the prefixes;
    private static final String PREFIX_PREF_DOT_NAME = "dot_name_";
    private static final String PREFIX_PREF_DOT_PHONE = "dot_phone_";
    private static final String PREFIX_PREF_DOT_IMAGE_URL = "dot_image_url_";
    private static final String PREF_ACTIVE_ACCOUNT_TOKEN = "chosen_account";
    private static final String PREFIX_PREF_DOT_EMAIL = "dot_email";
    private static final String PREFIX_PREF_DOT_GENDER = "dot_gender";
    private static final String PREFIX_PREF_DOT_FACECUT = "dot_facecut";
    private static final String PREFIX_PREF_DOT_FIRST_TIME = "dot_first_time";
    private static final String PREFIX_PREF_DOT_LOCATION_ADDRESS = "dot_address";
    private static final String PREFIX_PREF_DOT_LOCATION_LATLONG = "dot_lat_long";


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
        mPref.edit().putString(PREFIX_PREF_DOT_NAME, name).apply();
    }

    // Returns username
    public String getName() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_DOT_NAME, null) : null;
    }

    public void setPhone(final String phoneNumber) {
        mPref.edit().putString(PREFIX_PREF_DOT_PHONE, phoneNumber);
    }

    public String getPhone() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_DOT_PHONE, null) : null;
    }

    public void setImageUrl(final String imageUrl) {
        mPref.edit().putString(PREFIX_PREF_DOT_IMAGE_URL, imageUrl).apply();
    }

    public String getImageUrl() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_DOT_IMAGE_URL, null) : null;
    }

    public void setEmail(final String email) {

        mPref.edit().putString(PREFIX_PREF_DOT_EMAIL, email).apply();
    }

    public String getEmail() {
        return hasActiveAccount() ? mPref.getString(PREFIX_PREF_DOT_EMAIL, null) : null;
    }

    public void setGender(final String gender) {
        mPref.edit().putString(PREFIX_PREF_DOT_GENDER, gender).apply();
    }

    public void setFacecut(final String facecut) {
        mPref.edit().putString(PREFIX_PREF_DOT_FACECUT, facecut).apply();
    }

    public boolean hasProfileFilled() {
        return !TextUtils.isEmpty(getName());
    }

    public boolean isFirstTime() {
        return mPref.getBoolean(PREFIX_PREF_DOT_FIRST_TIME, true);
    }

    public void setFirstTime(boolean firstTime){
        mPref.edit().putBoolean(PREFIX_PREF_DOT_FIRST_TIME, firstTime).apply();
    }

    public void setAddress(String address){
        mPref.edit().putString(PREFIX_PREF_DOT_LOCATION_ADDRESS, address).apply();
    }

    public String getAddress(){
        return mPref.getString(PREFIX_PREF_DOT_LOCATION_ADDRESS, null);
    }

    public void setLatLong(String latLong){
        mPref.edit().putString(PREFIX_PREF_DOT_LOCATION_LATLONG, latLong).apply();
    }

    public String latLong(){
        return mPref.getString(PREFIX_PREF_DOT_LOCATION_LATLONG, null);
    }

}
