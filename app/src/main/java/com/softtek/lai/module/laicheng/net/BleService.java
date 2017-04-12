package com.softtek.lai.module.laicheng.net;

import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.BleTokenResponse;
import com.softtek.lai.module.laicheng.model.UploadImpedanceModel;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.okhttp.Response;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jia.lu on 2017/3/29.
 */

public interface BleService {
    @FormUrlEncoded
    @POST("/")
    void getBleToken(@Field("grant_type") String type,
                     @Field("client_id") String clientId,
                     @Field("client_secret") String client_secret,
                     RequestCallback<BleTokenResponse> callback);

    @POST("/DataSync/UploadData")
    void uploadImpedance(@Body UploadImpedanceModel impedanceModel,
                         @Query("accountId") Long accountId,
                         @Query("type") int type,
                         RequestCallback<BleMainData> callback);

    @FormUrlEncoded
    @POST("/")
    void checkMac(@Field("access_token")String token,@Field("macid") String mac,RequestCallback<retrofit.client.Response> callback);
}
