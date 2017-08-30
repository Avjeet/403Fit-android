package com.ccloudapp.fit403.ui.users;

import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.data.model.UserPublic;
import com.ccloudapp.fit403.ui.base.BasePresenter;
import com.ccloudapp.fit403.ui.base.BaseView;

import java.util.List;

/**
 * Created by Amit on 30/8/17.
 */

public interface UserProfileContract {
    interface View extends BaseView{
        void showProgressBar();

        void dismissProgressBar();

        void showErrorMsg(String msg);

        void showSuccessMsg(String msg);

        void showUsers(User user);
    }

    interface Presenter extends BasePresenter<View> {

        void getUser(String userId);
    }
}
