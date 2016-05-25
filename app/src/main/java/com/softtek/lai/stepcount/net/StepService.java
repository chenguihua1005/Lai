package com.softtek.lai.stepcount.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by jerry.guan on 5/25/2016.
 *
 */
public interface StepService {

    @FormUrlEncoded
    @POST("/StepCount/SaveStepCount")
    void synStepCount(@Field("AccountId") long accountId,
                      @Field("DateTimeTotalStep")String data,
                      RequestCallback<ResponseData> callback);
}
