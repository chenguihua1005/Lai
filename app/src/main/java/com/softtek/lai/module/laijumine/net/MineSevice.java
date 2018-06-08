package com.softtek.lai.module.laijumine.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.laijumine.model.FansInfoModel;
import com.softtek.lai.module.laijumine.model.FocusInfoModel;
import com.softtek.lai.module.laijumine.model.MyInfoModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 2/8/2017.
 */

public interface MineSevice {
    //我的
    @GET("/v1/HerbUser/GetMineInfo")
    void GetMyInfo(
            @Header("token")String token,
            @Query("AccountId")long AccountId,
            Callback<ResponseData<MyInfoModel>>callback
    );
    //粉丝列表
    @GET("/v1/HerbUser/GetLovePelist")
    void GetLovePelist(
            @Header("token")String token,
            @Query("AccountId")long AccountId,
            Callback<ResponseData<List<FansInfoModel>>>callback
    );
    //关注列表
    @GET("/v1/HerbUser/GetFocusPelist")
    void GetFocusPelist(
            @Header("token")String token,
            @Query("AccountId")long AccountId,
            Callback<ResponseData<List<FocusInfoModel>>>callback
    );
}
