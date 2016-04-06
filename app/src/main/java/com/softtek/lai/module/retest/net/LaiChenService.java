package com.softtek.lai.module.retest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.retest.model.ClientModel;
import com.softtek.lai.module.retest.model.MeasureModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 4/6/2016.
 */
public interface LaiChenService {
    //莱秤接口
    @POST("/oauth/token/")
    void doPostClient(
            @Query("grant_type")String grant_type,
            @Query("client_id")String client_id,
            @Query("client_secret")String client_secret,
            Callback<ResponseData<ClientModel>> callback
    );

    @GET("/DataSync/GetMeasuredData")
    void doGetMeasure(
            @Query("accesstoken")String accesstoken,
            @Query("phone")String phone,
            Callback<ResponseData<MeasureModel>>callback

    );

}
