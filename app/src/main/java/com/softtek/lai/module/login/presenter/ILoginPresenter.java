/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.app.ProgressDialog;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface ILoginPresenter {

    //登录
    void doLogin(String userName, String password, ProgressDialog dialog);

    //资格认证
    void alidateCertification(String memberId, String password, String accountId,ProgressDialog progressDialog);

    //修改头像
    void modifyPicture(String memberId, String upimg, ProgressDialog dialog,ImageView imgV);

    //修改头像
    void modifyPictures(String memberId, String upimg, ProgressDialog dialog);

    //修改昵称
    void getUpdateName(String accountId, String userName, ProgressDialog dialog);

    //获取环信帐号
    void getEMChatAccount( ProgressDialog dialog);
}
