package com.softtek.lai.module.healthyreport.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.healthyreport.model.HealthModel;
import com.softtek.lai.module.healthyreport.model.HealthyChartModel;
import com.softtek.lai.module.healthyreport.model.HealthyReport;
import com.softtek.lai.module.healthyreport.model.HealthyShareData;
import com.softtek.lai.module.healthyreport.model.LastestRecordModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zcy on 2016/4/18.
 */
public interface HealthyRecordService {
    //健康记录手动录入
    @POST("/V1/HealthRecords/SaveHealthRecord")
    void entryhealthrecord(@Header("token") String token,
                           @Body HealthModel healthModel,
                           Callback<ResponseData> callback);


    // //获取最新健康记录
    @GET("/V1/HealthRecords/GetUserMeasuredInfo")
    void getUserMeasuredInfo(
            @Header("token") String token,
//            @Query("accountId") long accountid, //学员id
            @Query("phone") String phone, //电话号码
            Callback<ResponseData<LastestRecordModel>> callback);

    //获取健康报告
    @GET("/v1/DataSync/GetLBRecordById")
    void getHealthyReport(@Header("token")String token,
                          @Query("recordId")String reportId,
                          RequestCallback<ResponseData<HealthyReport>> callback);

    //获取历史数据（曲线图）
    @GET("/v1/DataSync/GetLBHistory")
    void getLBHistory(@Header("token")String token,
                      @Query("recordId")String recordId,
                      @Query("paramsId")int pid,
                      RequestCallback<ResponseData<HealthyChartModel>> callback);
    //莱称曲线图
    @GET("/v1/DataSync/GetLBLineChart")
    void getLBLineChart(@Header("token")String token,
                        @Query("accountId")String accountId,
                        @Query("paramsId")int pid,
                        @Query("type")int type,
                        @Query("lastDate")String lastDate,
                        @Query("direction")int direction,
                        RequestCallback<ResponseData<HealthyChartModel.ChartBean>> callback);

    //获取分享数据
    @GET("/v1/DataSync/ShareRecord")
    void getShareData(@Header("token")String token,
                      @Query("recordId")String recordId,
                      RequestCallback<ResponseData<HealthyShareData>> callback);

}