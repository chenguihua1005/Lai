/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import com.ggx.widgets.view.CustomProgress;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface ILoginPresenter {

    //登录
    void doLogin(String userName, String password, CustomProgress dialog, TextView tv);

    //修改昵称
    void getUpdateName(String accountId, String userName, ProgressDialog dialog);

    //获取环信帐号
    void getEMChatAccount(ProgressDialog dialog);

    //前台注册后，更新后台环信
    void updateHXState(String phoneNo, String hxAccountId, String state, ProgressDialog dialog, DialogInterface dialogs, String type);
}
