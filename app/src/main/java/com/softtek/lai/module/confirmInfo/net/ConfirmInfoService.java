package com.softtek.lai.module.confirmInfo.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.confirmInfo.model.ConinfoModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by zcy on 2016/4/13.
 */
public interface ConfirmInfoService {

    // 修改参赛数据
    @POST("/MsgCenter/UpConfirmInfo")
    void changeUpConfirmInfo(@Header("token") String token,
                             @Body ConinfoModel coninfoModel,
                             Callback<ResponseData<ConinfoModel>> callback);
}
