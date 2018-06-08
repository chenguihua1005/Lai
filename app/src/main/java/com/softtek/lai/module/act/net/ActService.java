package com.softtek.lai.module.act.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.act.model.ActDetailModel;
import com.softtek.lai.module.act.model.ActZKModel;
import com.softtek.lai.module.act.model.ActZKPModel;
import com.softtek.lai.module.act.model.ActivityModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public interface ActService {

    String TOKEN = "token";

    //活动列表
    @GET("/Activity/ActivityList")
    void activityList(@Header(TOKEN) String token,
                      @Query("PageIndex") String pageIndex,
                      @Query("Accountid") String accountid,
                      RequestCallback<ResponseData<ActivityModel>> callback);

    //活动详情—简介
    @GET("/Activity/GetActivityIntro")
    void getActDetails(@Header(TOKEN) String token,
                      @Query("Activityid") String activityid,
                      RequestCallback<ResponseData<ActDetailModel>> callback);

    //活动详情—战况
    @GET("/Activity/GetActivitySituation")
    void getActivitySituation(@Header(TOKEN) String token,
                      @Query("PageIndex") String pageIndex,
                      @Query("Accountid") String accountid,
                      @Query("Activityid") String activityid,
                      RequestCallback<ResponseData<ActZKModel>> callback);

    //活动详情—战况
    @GET("/Activity/GetActRGStepOrder")
    void getActRGStepOrder(@Header(TOKEN) String token,
                      @Query("PageIndex") String pageIndex,
                      @Query("Rgid") String rgid,
                      @Query("Activityid") String activityid,
                      RequestCallback<ResponseData<ActZKPModel>> callback);

}
