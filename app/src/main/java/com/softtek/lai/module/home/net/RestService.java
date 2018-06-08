package com.softtek.lai.module.home.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.LaichModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 3/21/2016.
 */
public interface RestService {

    @GET("/HerbUser/GetUserMeasuredInfo")
    void GetUserMeasuredInfo(
            @Header("token") String token,
            @Query("phone") String phone,
            Callback<ResponseData<LaichModel>>callback
    );

}
