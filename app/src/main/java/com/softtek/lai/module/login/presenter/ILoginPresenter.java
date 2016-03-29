package com.softtek.lai.module.login.presenter;

import android.app.ProgressDialog;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface ILoginPresenter {

    //登录
    void doLogin(String userName, String password, ProgressDialog dialog);
}
