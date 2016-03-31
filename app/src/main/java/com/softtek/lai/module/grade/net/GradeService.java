/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.model.Banner;
import com.softtek.lai.module.grade.model.Grade;
import com.softtek.lai.module.grade.model.SRInfo;
import com.softtek.lai.module.grade.model.Student;
import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import java.util.List;

/**
 * Created by jerry.guan on 3/21/2016.
 */
public interface GradeService {

    //获取班级主页信息
    @GET("/HerbrClass/GetClassDetail")
    void getGradeInfo(@Header("token") String token,
                      @Query("ClassId") String classId,
                      Callback<ResponseData<Grade>> gradeInfo);

    //发布动态
    @FormUrlEncoded
    @POST("/HerbrClass/CreatDynamic")
    void senDynamic(@Header("token") String token,
                    @Field("ClassId") long classId,
                    @Field("DyInfoTitle") String dyTitle,
                    @Field("DyContent") String dyContent,
                    @Field("DyType") int dyType,
                    @Field("AccountId") long accountId,
                    Callback<ResponseData> callback);

    //获取学员列表
    @GET("/HerbrClass/SearchClassMember")
    void getStudentsList(@Header("token") String token,
                         @Query("classId") String classId,
                         @Query("ordertype") String orderType,
                         Callback<ResponseData<List<Student>>> callback);

    //获取助教列表
    @GET("/HerbrClass/GetSRList")
    void getTutorList(@Header("token") String token,
                      @Query("classid") long classId,
                      Callback<ResponseData<List<SRInfo>>> callback);

    //修改班级banner
    @Multipart
    @POST("/HerbrClass/UpClassImg")
    void updateClassBanner(@Header("token") String token,
                           @Query("ClassId") long classId,
                           @Query("type") String type,
                           @Part("photo") TypedFile photo,
                           Callback<ResponseData<Banner>> callback);
}
