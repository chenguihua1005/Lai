package com.softtek.lai.module.login.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.login.model.Identify;
import com.softtek.lai.module.login.model.Regist;
import com.softtek.lai.module.login.model.User;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.HEAD;
import retrofit.http.Header;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 3/3/2016.
 *
 */
public interface LoginService {

    @FormUrlEncoded
    @POST("/HerbUser/UserLogIn")
    void doLogin(
                 @Field("user")String userName,
                 @Field("psd")String password,
                 Callback<ResponseData<User>> callback);

    @GET("/HerbUser/GetVerificationNum")
    void getIdentify(
                     @Query("phone") String phone,
                     @Query("status") String status,
                     Callback<ResponseData<Identify>> callback);

    @FormUrlEncoded
    @POST("/HerbUser/UserRegister")
    void doRegist(
                  @Field("user") String userName,
                  @Field("psd") String password,
                  Callback<ResponseData<Regist>> callback);

    @FormUrlEncoded
    @POST("/HerbUser/ResetPassWord")
    void doResetPassword(
                         @Field("phone")String phone,
                         @Field("newpsd")String newPassword,
                         Callback<ResponseData> callback);


}
