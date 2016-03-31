/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.presenter;

/**
 * Created by jerry.guan on 3/4/2016.
 */
public interface IPasswordPresenter {

    //重置密码
    void resetPassword(String phone, String password, String identify);

    //校验验证码是否正确
    void checkIdentify(String phone, String identify);
}
