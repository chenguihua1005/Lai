package com.softtek.lai.module.ranking.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.ranking.model.OrderInfo;
import com.softtek.lai.module.ranking.model.RankModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 10/18/2016.
 */
public interface RankingService {

    //日排名
    @GET("/StepCount/NewGetCurrentDateOrder")
    void getCurrentDateOrder(@Header("token") String token,
                             @Query("RGIdType") int RGIdType,
                             @Query("PageIndex")int pageIndex,
                             RequestCallback<ResponseData<RankModel>> callback);

    //当周排名
    @GET("/StepCount/NewGetCurrentWeekOrder")
    void getCurrentWeekOrder(@Header("token") String token,
                             @Query("RGIdType") int RGIdType,
                             @Query("PageIndex")int pageIndex,
                             RequestCallback<ResponseData<RankModel>> callback);

    //日排名点赞
    @FormUrlEncoded
    @POST("/StepCount/InsertStepPrasie")
    void dayRankZan(@Header("token")String token,
                    @Field("AccountId")long accountId,
                    @Field("HealthId")String id,
                    RequestCallback<ResponseData> callback);

    //当日排名
    @GET("/StepCount/GetCurrentDateOnwOrder")
    void getDayOrder(@Header("token")String token,
                     @Query("RGIdType")int rdIdType,
                     RequestCallback<ResponseData<OrderInfo>> callback);
    //当周排名
    @GET("/StepCount/GetCurrentWeekOrderOwn")
    void getWeekOrder(@Header("token")String token,
                      @Query("RGIdType")int rdIdType,
                      RequestCallback<ResponseData<OrderInfo>> callback);
}
