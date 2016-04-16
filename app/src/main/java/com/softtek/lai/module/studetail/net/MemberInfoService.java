/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.studetail.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.lossweightstory.model.Zan;
import com.softtek.lai.module.studetail.model.LogList;
import com.softtek.lai.module.studetail.model.LossWeightLogModel;
import com.softtek.lai.module.studetail.model.MemberModel;
import com.softtek.lai.module.studetail.model.StudentLinChartInfoModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by julie.zhu on 3/22/2016.
 */
public interface MemberInfoService {

    public static final String TOKEN="token";

    @GET("/HerbrClass/GetClassMemberInfo")
    void getmemberInfo(@Header(TOKEN) String token,
                       @Query("userId") String userId,
                       @Query("classId") String classId,
                       Callback<ResponseData<MemberModel>> callback);

    //获取学员详情页面曲线图信息
    @GET("/HerbrClass/GetClassMemberInfoCurve")
    void getLineChartData(@Header(TOKEN)String token,
                          @Query("userId")String userId,
                          @Query("classId")String classId,
                          Callback<ResponseData<List<StudentLinChartInfoModel>>> callback);

    //获取学员减重日志
    @GET("/CompetitionLog/GetCompetitionLog")
    void getCompetitionLog(@Header(TOKEN)String token,
                           @Query("accountId")long accountId,
                           @Query("pageIndex")int pageIndex,
                           Callback<ResponseData<LogList>> callback);

    //点赞
    @FormUrlEncoded
    @POST("/CompetitionLog/ClickLike")
    void clickLike(@Header(TOKEN)String token,
                   @Field("accountId")long accountId,
                   @Field("logId")long logId,
                   Callback<ResponseData<Zan>> callback);

}
