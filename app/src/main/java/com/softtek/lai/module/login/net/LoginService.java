/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.login.model.IdentifyModel;
import com.softtek.lai.module.login.model.UserModel;
import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by jerry.guan on 3/3/2016.
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("/HerbUser/UserLogIn")
    void doLogin(
            @Field("user") String userName,
            @Field("psd") String password,
            Callback<ResponseData<UserModel>> callback);

    @FormUrlEncoded
    @POST("/HerbUser/GetVerificationNum")
    void getIdentify(
            @Field("phone") String phone,
            @Field("status") String status,
            Callback<ResponseData<IdentifyModel>> callback);

    @FormUrlEncoded
    @POST("/HerbUser/UserRegister")
    void doRegist(
            @Field("user") String userName,
            @Field("psd") String password,
            @Field("identify") String identify,
            Callback<ResponseData<UserModel>> callback);

    @FormUrlEncoded
    @POST("/HerbUser/ResetPassWord")
    void doResetPassword(
            @Field("phone") String phone,
            @Field("newpsd") String newPassword,
            @Field("identify") String identify,
            Callback<ResponseData> callback);


    @FormUrlEncoded
    @POST("/HerbUser/ValidationResetPassWord")
    void checkIdentify(@Field("phone") String phone,
                       @Field("identify") String identify,
                       Callback<ResponseData> callback);
}
