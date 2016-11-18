package com.softtek.lai.module.bodygame3.more.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.more.model.SmallRegion;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by jerry.guan on 11/18/2016.
 * 更多界面接口
 */

public interface MoreService {


    //获取小区列表
    @GET("/V1/MoreFunction/GetRegionalAndCitys")
    void getRegionalAndCitys(@Header("token")String token,
                             Callback<ResponseData<List<SmallRegion>>> callback);

}
