package com.softtek.lai.stepcount.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * Created by jerry.guan on 5/25/2016.
 *
 */
public interface StepNetService {

    @FormUrlEncoded
    @POST("/StepCount/SaveStepCount")
    void synStepCount(@Header("token")String token,
                      @Field("AccountId") long accountId,
                      @Field("DateTimeTotalStep")String data,
                      RequestCallback<ResponseData> callback);

    //移动跑团
    @POST("/HerbSports/UpdateIsMove")
    void updateIsMove(@Header("token")String token,
                      RequestCallback<ResponseData> callback);

}
