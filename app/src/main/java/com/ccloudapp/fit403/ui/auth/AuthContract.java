package com.ccloudapp.fit403.ui.auth;

import com.ccloudapp.fit403.ui.base.BasePresenter;
import com.ccloudapp.fit403.ui.base.BaseView;

/**
 * Created by dev on 12/8/17.
 */

public interface AuthContract {

    interface View extends BaseView {
        void showProgressDialog(String msg);

        void dismissProgressDialog();

        void showErrorMsg(String msg);

        void showSuccessMsg(String msg);
    }

    interface Presenter extends BasePresenter<View>{
        void onLogin(String username, String password);
    }
}
