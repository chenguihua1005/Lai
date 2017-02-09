package com.softtek.lai.module.laijumine.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laijumine.model.MyInfoModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 2/8/2017.
 */

public interface MineSevice {
    @GET("/v1/HerbUser/GetMineInfo")
    void GetMyInfo(
            @Header("token")String token,
            @Query("AccountId")long AccountId,
            Callback<ResponseData<MyInfoModel>>callback
    );
}
