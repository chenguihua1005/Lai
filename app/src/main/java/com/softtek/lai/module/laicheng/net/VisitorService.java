package com.softtek.lai.module.laicheng.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laicheng.model.GetVisitorModel;
import com.softtek.lai.module.laicheng.model.HistoryModel;
import com.softtek.lai.module.laicheng.model.VisitorModel;
import com.softtek.lai.module.laicheng.model.Visitsmodel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by shelly.xu on 4/10/2017.
 */

public interface VisitorService {

    @POST("/v1/LaiBalanceSync/SaveLBVisitor")
    void commitvisit(
            @Header("token") String token,
            @Body VisitorModel visitorModel,
            Callback<ResponseData<Visitsmodel>> callback
            );

    @GET("/v1/DataSync/GetLastRecord")
    void getData(
            @Header("token") String token,
            @Query("type") int type,
            Callback<ResponseData<GetVisitorModel>> callback);


    //历史访客信息列表
    @GET("/v1/DataSync/GetVisitorHistory")
    void doGetVisitHistory(
            @Header("token") String token,
            Callback<ResponseData<List<HistoryModel>>>callback

    );
}
