package com.softtek.lai.module.ranking.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.ranking.model.RankModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 10/18/2016.
 */
public interface RankingService {

    //日排名
    @GET("/StepCount/GetCurrentDateOrder")
    void getCurrentDateOrder(@Header("token") String token,
                             @Query("RGIdType") int RGIdType,
                             @Query("PageIndex")int pageIndex,
                             RequestCallback<ResponseData<RankModel>> callback);

    //当周排名
    @GET("/StepCount/GetCurrentWeekOrder")
    void getCurrentWeekOrder(@Header("token") String token,
                             @Query("RGIdType") int RGIdType,
                             @Query("PageIndex")int pageIndex,
                             RequestCallback<ResponseData<RankModel>> callback);
}
