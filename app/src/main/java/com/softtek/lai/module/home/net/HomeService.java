package com.softtek.lai.module.home.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.HomeInfo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public interface HomeService {

    @GET("/Index/IndexInfo")
    void doLoadHomeData(Callback<ResponseData<List<HomeInfo>>> callback);

    @GET("/Index/PageInfo")
    void getActivityByPage(@Query("img_type")int img_type,
                           @Query("pageindex")int page,
                           Callback<ResponseData<List<HomeInfo>>> callback);
}
