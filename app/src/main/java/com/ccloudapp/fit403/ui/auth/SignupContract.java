package com.ccloudapp.fit403.ui.auth;

import com.ccloudapp.fit403.data.model.User;
import com.ccloudapp.fit403.ui.base.BasePresenter;
import com.ccloudapp.fit403.ui.base.BaseView;

/**
 * Created by dev on 26/8/17.
 */

public class SignupContract {
    interface View extends BaseView {
        void showProgressDialog(String msg);

        void dismissProgressDialog();

        void showErrorMsg(String msg);

        void showSuccessMsg(String msg);

        void showDummyActivity(String username, String email);
    }

    interface Presenter extends BasePresenter<View>{

        void register(User user);
    }
}
