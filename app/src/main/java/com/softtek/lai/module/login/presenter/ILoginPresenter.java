/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.app.ProgressDialog;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface ILoginPresenter {

    //登录
    void doLogin(String userName, String password, ProgressDialog dialog);
}
