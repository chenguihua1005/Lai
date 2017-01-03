/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

import android.widget.Button;
import android.widget.EditText;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface IRegistPresenter {

    //注册
    void doRegist(String userName, String password,String HxAccountId,String identify, Button regist);

    //获取验证码
    void getIdentify(String phone, String state);

    //获取密码前的验证
    boolean validateGetIdentify(EditText et_phone, EditText et_password, EditText et_rePassword);

}
