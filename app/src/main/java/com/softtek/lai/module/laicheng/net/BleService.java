package com.softtek.lai.module.laicheng.net;

import com.softtek.lai.module.laicheng.model.BleMainData;
import com.softtek.lai.module.laicheng.model.BleTokenResponse;
import com.softtek.lai.module.laicheng.model.UploadImpedanceModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

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

    @POST("/")
    void uploadImpedance(@Body UploadImpedanceModel impedanceModel, RequestCallback<BleMainData> callback);
}
