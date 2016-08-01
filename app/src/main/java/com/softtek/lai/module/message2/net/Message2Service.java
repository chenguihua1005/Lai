/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.message.model.MessageModel;
import com.softtek.lai.module.message2.model.NoticeMsgModel;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.model.UnreadMsgModel;

import java.util.List;

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
public interface Message2Service {
    @GET("/MsgCenter/UnreadMsg")
    void getUnreadMsg(@Header("token") String token,
                    @Query("accountid") String accountid,
                    Callback<ResponseData<UnreadMsgModel>> callback);

    @GET("/MsgCenter/GetNoticeMsg")
    void getNoticeMsg(@Header("token") String token,
                    @Query("accountid") String accountid,
                    Callback<ResponseData<List<NoticeMsgModel>>> callback);

    @GET("/MsgCenter/GetOperateMsg")
    void getOperateMsg(@Header("token") String token,
                    @Query("accountid") String accountid,
                    Callback<ResponseData<List<OperateMsgModel>>> callback);

    @GET("/MsgCenter/GetMeasureMsg")
    void getMeasureMsg(@Header("token") String token,
                    @Query("accountid") String accountid,
                    Callback<ResponseData<List<NoticeMsgModel>>> callback);

    @POST("/MsgCenter/UpReadTime")
    void upRedTime(@Header("token") String token,
                    @Query("msgtype") String msgtype,
                    @Query("msgid") String msgid,
                    Callback<ResponseData> callback);

    @POST("/MsgCenter/RefuseRemoveSR")
    void refuseRemoveSR(@Header("token") String token,
                    @Query("msgid") String msgid,
                    Callback<ResponseData> callback);

    @POST("/MsgCenter/DeleteOneOrMoreMsg")
    @FormUrlEncoded
    void deleteOneMsg(@Header("token") String token,
                    @Field("msgtype") String msgtype,
                    @Field("msgid") String msgid,
                    Callback<ResponseData> callback);
}
