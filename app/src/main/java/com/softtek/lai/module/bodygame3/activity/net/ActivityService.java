package com.softtek.lai.module.bodygame3.activity.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.activity.model.ActdetailModel;
import com.softtek.lai.module.bodygame3.activity.model.ActivityModel;
import com.softtek.lai.module.bodygame3.activity.model.ActivitydataModel;
import com.softtek.lai.module.bodygame3.activity.model.ActtypeModel;
import com.softtek.lai.module.bodygame3.activity.model.InitialDataModel;
import com.softtek.lai.module.bodygame3.activity.model.PostInitialData;
import com.softtek.lai.module.bodygame3.activity.model.TodaysModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import butterknife.InjectView;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by shelly.xu on 11/24/2016.
 */

public interface ActivityService {
    //活动界面初始数据请求路径:Api/V1/ClassActivity/GetClassActivitys
    @GET("/V1/ClassActivity/GetClassActivitys")
    void getactivity(
            @Header("classid") String classid,
            @Header("token") String token,
            @Query("AccountId") long AccountId,
            @Query("ClassId") String ClassId,
            @Query("date") String date,
            Callback<ResponseData<ActivitydataModel>> callback
    );

    //获取当天的活动列表请求路径:Api/V1/ ClassActivity / GetActivityCurrDayInfo
    @GET("/V1/ClassActivity/GetActivityCurrDayInfo")
    void gettoday(
            @Header("classid") String classid,
            @Header("token") String token,
            @Query("AccountId") long AccountId,
            @Query("ClassId") String ClassId,
            @Query("CurrentDate") String CurrentDate,
            Callback<ResponseData<TodaysModel>> callback
    );

    //活动类型请求路径:Api/V1/ ClassActivity / GetClassActivityType
    @GET("/V1/ClassActivity/GetClassActivityType")
    void getacttype(
            @Header("token") String token,
            Callback<ResponseData<List<ActtypeModel>>> callback
    );

    //活动详情接口请求路径:Api/V1/ ClassActivity / GetActivityDetial
    @GET("/V1/ClassActivity/GetActivityDetial")
    void getactdetail(
            @Header("token") String token,
            @Query("UID") long UID,
            @Query("activityID") String activityID,
            Callback<ResponseData<ActdetailModel>> callback
    );

    //报名活动接口请求路径:Api/V1/ ClassActivity / JoinActivity
    @POST("/V1/ClassActivity/JoinActivity")
    void signup(
            @Header("token") String token,
            @Query("UID") long UID,
            @Query("activityID") String activityID,
            Callback<ResponseData> callback
    );

    //删除活动请求路径:Api/V1/ ClassActivity / DeleteActivity
    @POST("/V1/ClassActivity/DeleteActivity")
    void deleteact(
            @Header("token") String token,
            @Query("UID") long UID,
            @Query("activityID") String activityID,
            Callback<ResponseData> callback
    );

    //退出活动请求路径:Api/V1/ ClassActivity / ExistActivity
    @POST("/V1/ClassActivity/ExistActivity")
    void exitact(@Header("token") String token,
                 @Query("UID") long UID,
                 @Query("activityID") String activityID,
                 Callback<ResponseData> callback
    );


    //创建活动请求路径:Api/V1/ ClassActivity / CreateClassActivity
    @POST("/V1/ClassActivity/CreateClassActivity")
    void commitact(
            @Header("classid") String classid,
            @Header("token") String token,
            @Body ActivityModel activityModel,
            Callback<ResponseData> callback
    );

    @GET("/v1/HerbalifeClass/GetClassMembersWithoutInitData")
    void getInitialData(
            @Header("token") String token,
            @Query("classid") String classId,
            RequestCallback<ResponseData<List<InitialDataModel>>> callback
    );

    @POST("/v1/HealthRecords/SaveClassInitHealthRecord")
    void saveClassInitHealthRecord(
            @Header("token") String token,
            @Body PostInitialData postInitialData,
            RequestCallback<ResponseData> callback
    );
}
