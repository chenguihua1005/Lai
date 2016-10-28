/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.model.Version;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

import java.util.List;

/**
 * Created by jerry.guan on 3/17/2016.
 */
public interface HomeService {

    @GET("/Index/IndexInfo")
    void doLoadHomeData(Callback<ResponseData<List<HomeInfoModel>>> callback);

    @GET("/Index/PageInfo")
    void getActivityByPage(@Query("img_type") int img_type,
                           @Query("pageindex") int page,
                           Callback<ResponseData<List<HomeInfoModel>>> callback);

    @GET("/AppVisionLog/GetNewAppVisionInfo")
    void checkNew(RequestCallback<ResponseData<Version>> callback);
}
