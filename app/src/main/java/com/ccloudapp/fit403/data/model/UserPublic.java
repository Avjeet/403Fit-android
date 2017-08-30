package com.ccloudapp.fit403.data.model;

import android.support.annotation.NonNull;

/**
 * Created by dev on 26/8/17.
 */

public class UserPublic  extends BaseItemAdapter implements Comparable<UserPublic>{
    public String username;
    public String user_id;
    public String gender;
    public String age;
    public boolean isRequestSent;
    public String subject;

    @Override
    public int compareTo(@NonNull UserPublic o) {
        return (o.isRequestSent == this.isRequestSent ? 0 : (this.isRequestSent ? -1 : 1));
    }
}
