/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.UnReadMsg;
import com.softtek.lai.module.message2.model.AiXinStudent;
import com.softtek.lai.module.message2.model.ApplyConfirm;
import com.softtek.lai.module.message2.model.ApplyModel;
import com.softtek.lai.module.message2.model.InvitationConfirmShow;
import com.softtek.lai.module.message2.model.NoticeMsgModel;
import com.softtek.lai.module.message2.model.OperateMsgModel;
import com.softtek.lai.module.message2.model.UnreadMsgModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * update by jerry.Guan on 29/11/2016.
 */
public interface Message2Service {
    @GET("/V1/MsgCenter/IsHasUnReadMsg")
    void getMessageRead(@Header("token") String token,
                        Callback<ResponseData<UnReadMsg>> callback);

    @GET("/V1/MsgCenter/UnReadMsgCnt")
    void getUnreadMsg(@Header("token") String token,
                    @Query("accountid") String accountid,
                    Callback<ResponseData<UnreadMsgModel>> callback);

    @GET("/MsgCenter/GetNoticeMsg")
    void getNoticeMsg(@Header("token") String token,
                    @Query("accountid") String accountid,
                    Callback<ResponseData<List<NoticeMsgModel>>> callback);

    //小助手类消息列表
    @GET("/V1/MsgCenter/GetOperateMsgList")
    void getOperateMsgList(@Header("token")String token,
                           @Query("accountid")long accountId,
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

    //参赛邀请详情
    @GET("/V1/MsgCenter/ShowJionClassInfo")
    void getInvitationDetail(@Header("token")String token,
                             @Query("MsgId")String msgId,
                             Callback<ResponseData<InvitationConfirmShow>> callback);
    //验证爱心学员的手机号码
    @GET("/v1/MsgCenter/GetAccountIdByMobile")
    void validatePhone(@Header("token")String token,
                       @Query("Mobile")String phone,
                       Callback<ResponseData<AiXinStudent>> callback);

    //确认/拒绝加入班级
    @GET("/V1/MsgCenter/MakeSureJoin")
    void makeSureJoin(@Header("token")String token,
                      @Query("MsgId")String msgId,
                      @Query("status")int status,
                      @Query("IntroducerId")long introducerId,
                      Callback<ResponseData> callback);
    //获取申请参数确认信息
    @GET("/V1/MsgCenter/ShowApplyJionClass")
    void getShenQingJoinInfo(@Header("token")String token,
                             @Query("MsgId")String msgId,
                             Callback<ResponseData<ApplyConfirm>> callback);
    //批准/拒绝申请加入班级
    @POST("/v1/HerbalifeClass/ApproveClassApply")
    void examine(@Header("token")String token,
                 @Body ApplyModel model,
                 Callback<ResponseData> callback);
}
