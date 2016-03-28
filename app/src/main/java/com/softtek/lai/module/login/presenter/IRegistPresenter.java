package com.softtek.lai.module.login.presenter;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface IRegistPresenter {

    //注册
    void doRegist(String userName, String password,EditText et_identify);

    //获取验证码
    void getIdentify(String phone,String state);

    //获取密码前的验证
    boolean validateGetIdentify(EditText et_phone, EditText et_password, EditText et_rePassword);

}
