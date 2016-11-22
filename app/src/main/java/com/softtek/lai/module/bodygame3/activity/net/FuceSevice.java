package com.softtek.lai.module.bodygame3.activity.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by lareina.qiao on 2016/11/22.
 */

public interface FuceSevice {
    //    初始数据获取接口
    @GET("/V1/HerbalifeClass/GetClassInfoDefaultHome")
    void getInitData(
            @Header("token") String token,
            @Query("accountid") String accountid,
            @Query("pagesize") int pagesize,
            Callback<ResponseData<ClassinfoModel>> callback
    );
}
