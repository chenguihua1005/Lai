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
                    Callback<ResponseData<MessageModel>> callback);

    @FormUrlEncoded
    @POST("/HerbrClass/AcceptInviterToClass")
    void acceptInviterToClass(@Header("token") String token,
                              @Field("Inviters") String inviters,
                              @Field("ClassId") String classId,
                              @Field("acceptType") String acceptType,       //拒绝:0,接受:1
                              Callback<ResponseData> callback);

}
