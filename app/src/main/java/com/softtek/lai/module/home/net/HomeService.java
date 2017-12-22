/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.home.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.home.model.HomeInfoModel;
import com.softtek.lai.module.home.model.Version;
import com.softtek.lai.utils.RequestCallback;
import com.squareup.okhttp.ResponseBody;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.Streaming;

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

    @GET("/V1/AppVisionLog/GetNewAppVisionInfo")
    void checkNew(@Header("vision_name") String visionName,
            RequestCallback<ResponseData<Version>> callback);

    //下载最新apk
    @GET("/UpFiles/apk/{apk}")
    void downloadFile(@Path("apk")String apk,Callback<Response> callback);
}
