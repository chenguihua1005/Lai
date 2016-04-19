package com.softtek.lai.module.healthrecords.net;

import android.support.v7.util.SortedList;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.healthrecords.model.HealthModel;
import com.softtek.lai.module.healthrecords.model.LastestRecordModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zcy on 2016/4/18.
 */
public interface HealthRecordService {
    //健康记录手动录入
    @POST("/HealthRecords/SaveHealthRecord")
    void entryhealthrecord(@Header("token") String token,
                           @Query("accountId") long accountId,
                           @Body HealthModel healthModel,
                           Callback<ResponseData<HealthModel>> callback);

    //获取最新健康记录
    @GET("/HealthyCircle/GetLastestRecord")
    void doGetLastestRecord(
            @Header("token") String token,
            @Query("accountId") long accountid, //学员id
            Callback<ResponseData<LastestRecordModel>> callback);

}