package com.softtek.lai.module.healthyreport.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.healthyreport.model.HistoryDataModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public interface HistoryDataService {

    //获取历史记录列表
    @GET("/v1/DataSync/GetSelftHistory")
    void getHistoryDataList(@Header("token") String token,
                            @Query("type") int type,
                            @Query("accountId")long accountId,
                            @Query("pageIndex") int pageIndex,
                            RequestCallback<ResponseData<HistoryDataModel>> callback);

    //删除历史记录

    @POST("/v1/DataSync/DeleteLBHistory")
    void deleteHistoryData(@Header("token") String token,
                           @Query("type")int type,
                           @Query("recordIds")String repcordIds,
                           RequestCallback<ResponseData> callback);


}
