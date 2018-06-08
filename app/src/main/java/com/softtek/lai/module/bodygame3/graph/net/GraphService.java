package com.softtek.lai.module.bodygame3.graph.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.graph.model.GirthModel;
import com.softtek.lai.module.bodygame3.graph.model.WeightModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * @author jerry.Guan
 *         created by 2016/12/3
 */

public interface GraphService {

    //获取体重变化曲线图
    @GET("/V1/HerbalifeClass/GetClassMemberWeightChart")
    void getClassMemberWeightChart(
                                   @Header("token") String token,
                                   @Query("accountId") long accountId,
                                   @Query("classid") String classId,
                                   Callback<ResponseData<List<WeightModel>>> callback);

    //获取维度变化曲线图
    @GET("/V1/HerbalifeClass/GetClassMemberGirthChart")
    void getClassMemberGirthChart(
                                  @Header("token") String token,
                                  @Query("accountId") long accountId,
                                  @Query("classid") String classId,
                                  Callback<ResponseData<List<GirthModel>>> callback);
}
