package com.softtek.lai.module.sport.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.sport.model.HistorySportModel;
import com.softtek.lai.module.sport.model.MineMovementModel;
import com.softtek.lai.module.sport.model.SportData;
import com.softtek.lai.module.sport.model.TotalSportModel;
import com.softtek.lai.utils.RequestCallback;

import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 4/8/2016.
 */
public interface SportService {

    String TOKEN = "token";

    //运动列表
    @GET("/StepCount/GetMovementList")
    void getMovementList(@Header(TOKEN) String token,
                         @Query("PageIndex")String pageIndex,
                         RequestCallback<ResponseData<List<HistorySportModel>>> callback);


    //运动历史统计
    @GET("/StepCount/GetHistoryTotalMovement")
    void getHistoryTotalMovement(@Header(TOKEN) String token,
                         RequestCallback<ResponseData<TotalSportModel>> callback);

    //运动历史统计
    @GET("/StepCount/GetV")
    void getSportValue(@Header(TOKEN) String token,
                         RequestCallback<ResponseData<TotalSportModel>> callback);

    //运动历史统计
    @GET("/StepCount/GetMineMovement")
    void getMineMovement(@Header(TOKEN) String token,
                         @Query("accountid")String accountid,
                         RequestCallback<ResponseData<MineMovementModel>> callback);

    @POST("/StepCount/SaveMovement")
    void submitSportData(@Header(TOKEN) String token,
                         @Body SportData data,
                         RequestCallback<ResponseData> callback);

}
