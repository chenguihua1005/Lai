package com.softtek.lai.module.laisportmine.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laisportmine.model.RunTeamModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 5/10/2016.
 */
public interface MineService {
    @GET("/HerbMyData/GetNowRgName")
    void doGetNowRgName(
            @Header("token")String token,
            @Query("accountid")long accountid,
            Callback<ResponseData<RunTeamModel>>callback
    );
}
