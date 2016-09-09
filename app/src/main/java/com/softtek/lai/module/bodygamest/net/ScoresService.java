package com.softtek.lai.module.bodygamest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygamest.model.HistoryClassModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jarvis.Liu on 3/31/2016.
 */
public interface ScoresService {
    //荣誉榜
    @GET("/HerbrClass/GetHistoryClassList")
    void getHistoryClassList(
            @Header("token") String token,
            @Query("accountid") String accountid,
            Callback<ResponseData<List<HistoryClassModel>>> callback
    );
}
