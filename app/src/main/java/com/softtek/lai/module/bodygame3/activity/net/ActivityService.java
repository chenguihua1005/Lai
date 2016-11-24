package com.softtek.lai.module.bodygame3.activity.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.activity.model.ActivitydataModel;
import com.softtek.lai.module.bodygame3.activity.model.TodaysModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public interface ActivityService {
    //活动界面初始数据请求路径:Api/V1/ClassActivity/GetClassActivitys
    @GET("/V1/ClassActivity/GetClassActivitys")
    void getactivity(
            @Header("token") String token,
            @Query("AccountId") String AccountId,
            @Query("ClassId") String ClassId,
            Callback<ResponseData<ActivitydataModel>> callback
    );

    //获取当天的活动列表请求路径:Api/V1/ ClassActivity / GetActivityCurrDayInfo
    @GET("/V1/ClassActivity/GetActivityCurrDayInfo")
    void gettoday(
            @Header("token") String token,
            @Query("AccountId") String AccountId,
            @Query("ClassId") String ClassId,
            @Query("CurrentDate") String CurrentDate,
            Callback<ResponseData<TodaysModel>> callback
    );
}
