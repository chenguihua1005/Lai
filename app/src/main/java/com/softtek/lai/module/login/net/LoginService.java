/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.login.net;

import com.softtek.lai.chat.model.ChatContactInfoModel;
import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.login.model.EMChatAccountModel;
import com.softtek.lai.module.login.model.IdentifyModel;
import com.softtek.lai.module.login.model.PhotoModel;
import com.softtek.lai.module.login.model.RoleInfo;
import com.softtek.lai.module.login.model.UserModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

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

    @FormUrlEncoded
    @POST("/MineInfo/ChangePsd")
    void changePsd(
            @Header("token") String token,
            @Field("newpsd") String newpsd,
            @Field("psd") String psd,
            Callback<ResponseData> callback);

    @FormUrlEncoded
    @POST("/HerbUser/ValidateCertification")
    void alidateCertification(
            @Header("token") String token, @Field("memberId") String memberId,
            @Field("password") String password,
            @Field("accountId") String accountId,
            Callback<ResponseData<RoleInfo>> callback);

    //上传图片
    @POST("/MyBasicInformation/ModifyPicture")
    @Multipart
    void modifyPicture(
            @Header("token") String token,
            @Query("AccountId") String accountId,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<PhotoModel>> callback);

    @GET("/MyBasicInformation/GetUpdateName")
    void getUpdateName(
            @Header("token") String token,
            @Query("AccountId") String accountId,
            @Query("USerName") String userName,
            Callback<ResponseData> callback);

    @GET("/HerbUser/GetEMChatAccount")
    void getEMChatAccount(
            @Header("token") String token,
            Callback<ResponseData<EMChatAccountModel>> callback);

    @GET("/HerbUser/GetEMchatContacts")
    void getEMchatContacts(
            @Header("token") String token,
            Callback<ResponseData<List<ChatContactInfoModel>>> callback);
}
