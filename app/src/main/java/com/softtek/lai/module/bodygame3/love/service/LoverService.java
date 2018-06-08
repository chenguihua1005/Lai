package com.softtek.lai.module.bodygame3.love.service;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.love.model.LoverModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jessica.zhang on 1/5/2017.
 */

public interface LoverService {
    //获取爱心学员榜单
    @GET("/v1/MsgCenter/GetIntroducerList")
    void getIntroducerList(@Header("classid") String cId,
                           @Header("token") String token,
                           @Query("accountId") long accountId,
                           @Query("classId") String classId,
                           @Query("queryType") int queryType,//0：查询所有转介绍次数 1：当前班级转介绍次数
                           Callback<ResponseData<List<LoverModel>>> callback);

}
