package com.softtek.lai.module.historydate.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.historydate.model.HistoryDataModel;
import com.softtek.lai.module.historydate.model.ID;
import com.softtek.lai.utils.RequestCallback;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/20/2016.
 */
public interface HistoryDataService {

    //获取历史记录列表
    @GET("/HealthRecords/GetHealthHistory")
    void getHistoryDataList(@Header("token") String token,
                            @Query("accountid") long accountId,
                            @Query("pageIndex") int pageIndex,
                            RequestCallback<ResponseData<HistoryDataModel>> callback);

    //删除历史记录

    @POST("/HealthRecords/DelHealthHistory")
    void deleteHistoryData(@Header("token") String token,
                           @Body ID dataId,
                           RequestCallback<ResponseData> callback);


}
