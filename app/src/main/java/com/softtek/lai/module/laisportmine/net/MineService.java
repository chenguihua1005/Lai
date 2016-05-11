package com.softtek.lai.module.laisportmine.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laisportmine.model.PublicWewlfModel;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 5/10/2016.
 */
public interface MineService {
    //我的跑团
    @GET("/HerbMyData/GetNowRgName")
    void doGetNowRgName(
            @Header("token")String token,
            @Query("accountid")long accountid,
            Callback<ResponseData<RunTeamModel>>callback
    );
    //退出跑团
    @POST("/HerbMyData/SignOutRG")
    void doSignOutRG(
            @Header("token")String token,
            @Query("accountid")long accountid,
            Callback<ResponseData>callback
    );
    //慈善列表
    @GET("/SportMsg/GetDonateMsg")
    void GetDonateMsg(
            @Header("token")String token,
            @Query("accountid")long accountid,
            Callback<ResponseData<PublicWewlfModel>>callback
    );
}
