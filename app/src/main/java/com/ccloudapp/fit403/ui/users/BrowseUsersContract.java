package com.ccloudapp.fit403.ui.users;

import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.ui.base.BasePresenter;
import com.ccloudapp.fit403.ui.base.BaseView;

import java.util.List;

/**
 * Created by Amit on 27/8/17.
 */

public interface BrowseUsersContract {

    interface View extends BaseView {

        void showProgressBar();

        void dismissProgressBar();

        void showErrorMsg(String msg);

        void showSuccessMsg(String msg);

        void showUsers(List<UserPublic> list);

        void revertView(int position);
    }

    interface Presenter extends BasePresenter<View> {

        void browseUsers();

        void acceptRequest(String userId, int position);

        void ignoreRequest(String userId, int position);

        void addFriend(String userId, int position);
    }
}
