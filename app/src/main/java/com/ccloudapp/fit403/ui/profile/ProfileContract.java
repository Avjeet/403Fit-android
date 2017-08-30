package com.ccloudapp.fit403.ui.profile;

import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.ui.base.BasePresenter;
import com.ccloudapp.fit403.ui.base.BaseView;

/**
 * Created by Amit on 27/8/17.
 */

public interface ProfileContract {
    interface View extends BaseView{
        void showProgressDialog(String msg);

        void dismissProgressDialog();

        void showErrorMsg(String msg);

        void showSuccessMsg(String msg);

        void showDummyActivity();
    }

    interface Presenter extends BasePresenter<View>{
        void updateProfile(User user);
    }
}
