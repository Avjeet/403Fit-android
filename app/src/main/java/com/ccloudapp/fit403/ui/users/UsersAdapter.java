package com.ccloudapp.fit403.ui.users;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccloudapp.fit403.R;
import com.ccloudapp.fit403.data.model.BaseItemAdapter;
import com.ccloudapp.fit403.data.model.Subheader;
import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.di.context.ActivityContext;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amit on 28/8/17.
 */

public class UsersAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<BaseItemAdapter> data;

    private final LayoutInflater mLayoutInflater;

    private static final int HEADER = 750;
    private static final int ITEM_FRIEND_REQ = 374;
    private static final int ITEM_OTHER = 138;


    @Inject
    public UsersAdapter(@ActivityContext Context context) {
        mContext = context;
        data = new ArrayList<>();
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setData(List<UserPublic> list) {
        boolean isRequestHeaderAdded = false;
        boolean isOtherPeopleHeaderAdded = false;
        for (int i = 0; i < list.size(); i++) {
            UserPublic userPublic = list.get(i);
            if (userPublic.isRequestSent && !isRequestHeaderAdded) {
                Subheader subheader = new Subheader();
                subheader.title = "Friend Requests";
                data.add(subheader);
                notifyItemInserted(data.size() - 1);
                isRequestHeaderAdded = true;
            } else if (!userPublic.isRequestSent && !isOtherPeopleHeaderAdded) {
                Subheader subheader = new Subheader();
                subheader.title = "Other People";
                data.add(subheader);
                notifyItemInserted(data.size() - 1);
                isOtherPeopleHeaderAdded = true;
            }

            data.add(userPublic);
            notifyItemInserted(data.size() - 1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEADER:
                return new SubheaderViewHolder(
                        mLayoutInflater.inflate(R.layout.subheader, parent, false));
            case ITEM_OTHER:
                View usersView = mLayoutInflater.inflate(R.layout.item_user_list, parent, false);
                UserViewHolder userViewHolder = new UserViewHolder(usersView);
                userViewHolder.mRelativeLayout.setOnClickListener(v -> {
                    if (mContext instanceof BrowseUsersActivity) {
                        ((BrowseUsersActivity) mContext).openProfileActivity(
                                ((UserPublic) getItem(
                                        userViewHolder.getAdapterPosition())).user_id);
                    }
                });
                return userViewHolder;
            case ITEM_FRIEND_REQ:
                View frndReqView = mLayoutInflater.inflate(R.layout.friendreq_item_user_list,
                        parent, false);
                FriendReqViewHolder friendReqViewHolder = new FriendReqViewHolder(frndReqView);
                friendReqViewHolder.mRelativeLayout.setOnClickListener(v -> {
                    if (mContext instanceof BrowseUsersActivity) {
                        ((BrowseUsersActivity) mContext).openProfileActivity(
                                ((UserPublic) getItem(
                                        friendReqViewHolder.getAdapterPosition())).user_id);
                    }
                });
                return friendReqViewHolder;
            default:
                View view = mLayoutInflater.inflate(R.layout.item_user_list, parent, false);
                return new UserViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case HEADER:
                bindHeaderViewHolder((SubheaderViewHolder) holder, position);
                break;
            case ITEM_OTHER:
                bindItemViewHolder((UserViewHolder) holder, position);
                break;
            case ITEM_FRIEND_REQ:
                bindFriendReqViewHolder((FriendReqViewHolder) holder, position);
                break;
        }

    }

    private void bindFriendReqViewHolder(FriendReqViewHolder holder, int position) {
        UserPublic userPublic = (UserPublic) getItem(position);
        holder.mUserNameTextView.setText(userPublic.username);
        holder.mUserAgeGenderTextView.setText(userPublic.age + " yrs old");
        if (userPublic.gender.equalsIgnoreCase("Male")) {
            holder.mUserAgeGenderTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_male_gender_sign, 0, 0, 0);
        } else {
            holder.mUserAgeGenderTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_female_sign, 0, 0, 0);
        }
        if (userPublic.subject != null) {
            holder.mSubjectFriendTextView.setText(userPublic.subject);
        } else {
            holder.mSubjectFriendTextView.setText("Hey there ! I am new to 403 fitness.");
        }
    }

    private void bindItemViewHolder(UserViewHolder holder, int position) {
        UserPublic userPublic = (UserPublic) getItem(position);
        holder.mUserNameTextView.setText(userPublic.username);
        holder.mUserAgeGenderTextView.setText(userPublic.age + " yrs old");
        if (userPublic.gender.equalsIgnoreCase("Male")) {
            holder.mUserAgeGenderTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_male_gender_sign, 0, 0, 0);
        } else {
            holder.mUserAgeGenderTextView.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.ic_female_sign, 0, 0, 0);
        }
        if (userPublic.subject != null) {
            holder.mSubjectFriendTextView.setText(userPublic.subject);
        } else {
            holder.mSubjectFriendTextView.setText("Hey there ! I am new to 403 fitness.");
        }
    }

    private void bindHeaderViewHolder(SubheaderViewHolder holder, int position) {
        Subheader subheader = (Subheader) getItem(position);
        holder.mTextView.setText(subheader.title);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Subheader) {
            return HEADER;
        } else {
            UserPublic userPublic = (UserPublic) getItem(position);
            if (userPublic.isRequestSent) {
                return ITEM_FRIEND_REQ;
            } else {
                return ITEM_OTHER;
            }
        }
    }

    public BaseItemAdapter getItem(int position) {
        return data.get(position);
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name_tv)
        TextView mUserNameTextView;
        @BindView(R.id.user_age_gender_tv)
        TextView mUserAgeGenderTextView;
        @BindView(R.id.add_friend_text_view)
        TextView mAddFriendTextView;
        @BindView(R.id.subject_text_view)
        TextView mSubjectFriendTextView;
        @BindView(R.id.user_profile_imageview)
        ImageView mUserProfileImageView;
        @BindView(R.id.main_content)
        RelativeLayout mRelativeLayout;


        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FriendReqViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_name_tv)
        TextView mUserNameTextView;
        @BindView(R.id.user_age_gender_tv)
        TextView mUserAgeGenderTextView;
        @BindView(R.id.confirm_friend_text_view)
        TextView mConfirmFriendTextView;
        @BindView(R.id.ignore_friend_text_view)
        TextView mIgnoreFriendTextView;
        @BindView(R.id.subject_text_view)
        TextView mSubjectFriendTextView;
        @BindView(R.id.user_profile_imageview)
        ImageView mUserProfileImageView;
        @BindView(R.id.main_content)
        RelativeLayout mRelativeLayout;

        public FriendReqViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class SubheaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.subheader_textview)
        TextView mTextView;

        public SubheaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
