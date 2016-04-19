/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.message.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.model.AssistantApplyInfoModel;
import com.softtek.lai.module.counselor.model.AssistantClassInfoModel;
import com.softtek.lai.module.counselor.model.AssistantDetailInfoModel;
import com.softtek.lai.module.counselor.model.AssistantInfoModel;
import com.softtek.lai.module.counselor.model.AssistantModel;
import com.softtek.lai.module.counselor.model.ClassIdModel;
import com.softtek.lai.module.counselor.model.ClassInfoModel;
import com.softtek.lai.module.counselor.model.HonorInfoModel;
import com.softtek.lai.module.counselor.model.InviteStudentInfoModel;
import com.softtek.lai.module.counselor.model.MarchInfoModel;
import com.softtek.lai.module.message.model.MessageModel;

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
public interface MessageService {
    @GET("/MsgCenter/MsgList")
    void getMsgList(@Header("token") String token,
                    @Query("accountid") String accountid,
                    Callback<ResponseData<MessageModel>> callback);

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

    @POST("/MsgCenter/DelNoticeOrMeasureMsg")
    void delNoticeOrMeasureMsg(@Header("token") String token,
                       @Query("MessageId") String messageId,
                       Callback<ResponseData> callback);

    @GET("/MessageRead/GetMessageRead")
    void getMessageRead(@Header("token") String token,
                       @Query("AccountID") String accountID,
                       Callback<ResponseData> callback);

    @FormUrlEncoded
    @POST("/MsgCenter/UpReadTime")
    void upReadTime(@Header("token") String token,
                    @Field("msgtype") String msgtype,        //1：复测消息 2：SP邀请SR信息 3：SR与PC申请信息 4：学员参赛信息
                    @Field("recevieid") String recevieid,
                    @Field("senderid") String senderid,
                    @Field("classid") String classid,
                    Callback<ResponseData> callback);

}
