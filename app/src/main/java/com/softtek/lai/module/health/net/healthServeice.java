package com.softtek.lai.module.health.net;

import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.HEAD;

/**
 * Created by John on 2016/4/12.
 */
public interface healthServeice {
    @GET("/ HealthRecords/ GetHealthRecords")
    void doGetHealth(
//            @HEAD("token")String token,
//            RequestCallback<>
    );
}
