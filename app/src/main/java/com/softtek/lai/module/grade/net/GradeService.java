package com.softtek.lai.module.grade.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.model.Grade;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public interface GradeService {

    //获取班级主页信息
    @GET("/HerbrClass/GetClassDetail")
    void getGradeInfo(@Header("token")String token,
                      @Query("ClassId")String classId,
                      Callback<ResponseData<Grade>> gradeInfo);
}
