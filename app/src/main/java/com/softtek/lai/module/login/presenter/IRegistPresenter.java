package com.softtek.lai.module.login.presenter;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface IRegistPresenter {

    //注册
    void doRegist(String userName, String password,String identify);

    //获取验证码
    void getIdentify(String phone,String state);

}
