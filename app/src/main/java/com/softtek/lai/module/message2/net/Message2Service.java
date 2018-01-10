/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message2.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.UnReadMsg;
import com.softtek.lai.module.message2.model.ActionNoticeModel;
import com.softtek.lai.module.message2.model.AiXinStudent;
import com.softtek.lai.module.message2.model.ApplyConfirm;
import com.softtek.lai.module.message2.model.ApplyModel;
import com.softtek.lai.module.message2.model.InvitationConfirmShow;
import com.softtek.lai.module.message2.model.NoticeModel;
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
                        @Query("accountid") long accountId,
                        Callback<ResponseData<UnReadMsg>> callback);

    @GET("/V1/MsgCenter/UnReadMsgCnt")
    void getUnreadMsg(@Header("token") String token,
                      @Query("accountid") String accountid,
                      Callback<ResponseData<UnreadMsgModel>> callback);

    //小助手类消息列表
    @GET("/V1/MsgCenter/GetOperateMsgList")
    void getOperateMsgList(@Header("token") String token,
                           @Query("accountid") long accountId,
                           Callback<ResponseData<List<OperateMsgModel>>> callback);

    //服务窗消息列表
    @GET("/V1/MsgCenter/GetMeasureMsgList")
    void getMeasureMsgList(@Header("token") String token,
                           @Query("accountid") long accountId,
                           Callback<ResponseData<List<NoticeModel>>> callback);

    //复测消息列表
    @GET("/V1/MsgCenter/GetNoticeMsgList")
    void getNoticeMsgList(@Header("token") String token,
                          @Query("accountid") long accountId,
                          Callback<ResponseData<List<NoticeModel>>> callback);

    //活动通知
    @GET("/V1/MsgCenter/GetActiveMsgList")
    void getActiveNoticeMsg(
            @Header("token") String token,
            @Query("accountid") long accountid,
            Callback<ResponseData<List<ActionNoticeModel>>> callback
    );


//    @POST("/MsgCenter/DeleteOneOrMoreMsg")
//    @FormUrlEncoded
//    void deleteOneMsg(@Header("token") String token,
//                    @Field("Msgtype") String msgtype,
//                    @Field("Msgid") String msgid,
//                    Callback<ResponseData> callback);

    //参赛邀请详情  /v1/Club/ShowJionClassInfo
    @GET("/V1/Club/ShowJionClassInfo")
    void getInvitationDetail(@Header("token") String token,
                             @Query("MsgId") String msgId,
                             Callback<ResponseData<InvitationConfirmShow>> callback);

    //验证爱心学员的手机号码
    @GET("/v1/MsgCenter/GetAccountIdByMobile")
    void validatePhone(@Header("token") String token,
                       @Query("Mobile") String phone,
                       @Query("classId") String classId,
                       Callback<ResponseData<AiXinStudent>> callback);

    //确认/拒绝加入班级
    @POST("/V1/Club/MakeSureJoin")
    void makeSureJoin(@Header("token") String token,
                      @Query("MsgId") String msgId,
                      @Query("status") int status,
                      @Query("IntroducerId") long introducerId,
                      @Query("target") int target,//参赛目标
                      Callback<ResponseData> callback);

    //获取申请参数确认信息  v1/club/ShowApplyJionClass
    @GET("/v1/club/ShowApplyJionClass")
    void getShenQingJoinInfo(@Header("token") String token,
                             @Query("MsgId") String msgId,
                             Callback<ResponseData<ApplyConfirm>> callback);

    //批准/拒绝申请加入班级
    @POST("/v1/HerbalifeClass/ApproveClassApply")
    void examine(@Header("token") String token,
                 @Body ApplyModel model,
                 Callback<ResponseData> callback);

    //删除单个或多个消息
    @FormUrlEncoded
    @POST("/V1/MsgCenter/DeleteOneOrMoreMsg")
    void deleteMssage(@Header("token") String token,
                      @Field("Msgids") String ids,
                      @Field("Msgtype") int msgType,
                      Callback<ResponseData> callback);
}
