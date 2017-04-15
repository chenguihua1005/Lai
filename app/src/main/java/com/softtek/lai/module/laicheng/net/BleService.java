package com.softtek.lai.module.laicheng.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.BleTokenResponse;
import com.softtek.lai.module.laicheng.model.LastInfoData;
import com.softtek.lai.module.laicheng.model.UploadImpedanceModel;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.okhttp.Response;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
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

    @POST("/v1/DataSync/UploadData")
    void uploadImpedance(@Header("token") String token,
                         @Body UploadImpedanceModel impedanceModel,
                         @Query("accountId") Long accountId,
                         @Query("type") int type,
                         RequestCallback<ResponseData<BleMainData>> callback);

    @FormUrlEncoded
    @POST("/")
    void checkMac(@Field("access_token")String token,
                  @Field("macid") String mac,RequestCallback<retrofit.client.Response> callback);

    @GET("/v1/DataSync/GetLastRecord")
    void getLastData(@Header("token") String token,
                     @Query("type")int type,
                     RequestCallback<ResponseData<LastInfoData>> callback);
}
