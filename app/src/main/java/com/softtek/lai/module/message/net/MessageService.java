/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.net;

import com.softtek.lai.common.ResponseData;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface MessageService {

    @GET("/MessageRead/GetMessageRead")
    void getMessageRead(@Header("token") String token,
                        Callback<ResponseData> callback);


    @FormUrlEncoded
    @POST("/HerbrClass/AcceptInviterToClass")
    void acceptInviterToClass(@Header("token") String token,
                              @Field("Inviters") String inviters,
                              @Field("ClassId") String classId,
                              @Field("acceptType") String acceptType,       //拒绝:0,接受:1
                              Callback<ResponseData> callback);

    @FormUrlEncoded
    @POST("/HerbrClass/AcceptInviter")
    void acceptInviter(@Header("token") String token,
                       @Field("Inviters") String inviters,
                       @Field("ClassId") String classId,
                       @Field("acceptType") String acceptType,       //拒绝:0,接受:1
                       Callback<ResponseData> callback);


    @GET("/MsgCenter/AccIsJoinClass")
    void accIsJoinClass(@Header("token") String token,
                        @Query("accountid") String accountid,
                        @Query("classid") String classid,
                       Callback<ResponseData> callback);

}
