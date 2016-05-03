package com.softtek.lai.module.mygrades.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.mygrades.model.DayRankModel;
import com.softtek.lai.module.mygrades.model.GradesModel;
import com.softtek.lai.module.mygrades.model.HonorModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by julie.zhu on 5/3/2016.
 */
public interface GradesService {
      //2.19.1	我的成绩
    @GET("/StepCount/GetStepCount")
    void getStepCount(@Header("token") String token,
                      Callback<ResponseData<GradesModel>> callback);

    //2.19.3	当日排名
    @GET("/StepCount/GetCurrentDateOrder")
    void getCurrentDateOrder(@Header("token") String token,
                             @Query("RGId")int RGId,
                             Callback<ResponseData<DayRankModel>> callback);

    //2.19.4	当周排名
    @GET("/StepCount/GetCurrentWeekOrder")
    void getCurrentWeekOrder(@Header("token") String token,
                             Callback<ResponseData<DayRankModel>> callback);

    //2.19.2	勋章详情页
    @GET("/StepCount/GetStepHonor")
    void getStepHonor(@Header("token") String token,
                      Callback<ResponseData<HonorModel>> callback);
}
