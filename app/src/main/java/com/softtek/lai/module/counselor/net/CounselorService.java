/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.counselor.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.counselor.model.ApplySuccessModel;
import com.softtek.lai.module.counselor.model.MarchInfoModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by jarvis.liu on 3/22/2016.
 */
public interface CounselorService {


    @FormUrlEncoded
    @POST("/HerbrClass/SendInviterMsg")
    void sendInviterMsg(@Header("token") String token,
                        @Field("Inviters") String inviters,
                        @Field("ClassId") String classId,
                        Callback<ResponseData> callback);


    @GET("/Index/GetMatchInfo")
    void getMatchInfo(@Header("token") String token,
                      @Query("dtime") String dtime,
                      @Query("group") String group,
                      Callback<ResponseData<List<MarchInfoModel>>> callback);


}
