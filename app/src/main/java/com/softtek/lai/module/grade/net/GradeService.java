/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.grade.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.model.BannerModel;
import com.softtek.lai.module.grade.model.DynamicInfoModel;
import com.softtek.lai.module.grade.model.GradeModel;
import com.softtek.lai.module.grade.model.SRInfoModel;
import com.softtek.lai.module.grade.model.StudentModel;
import com.softtek.lai.utils.RequestCallback;

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
                      Callback<ResponseData<GradeModel>> gradeInfo);

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
                         @Query("accountid")long accountId,
                         @Query("classId") String classId,
                         @Query("ordertype") String orderType,
                         Callback<ResponseData<List<StudentModel>>> callback);

    //获取助教列表
    @GET("/HerbrClass/GetSRList")
    void getTutorList(@Header("token") String token,
                      @Query("classid") long classId,
                      Callback<ResponseData<List<SRInfoModel>>> callback);

    //修改班级banner
    @Multipart
    @POST("/HerbrClass/UpClassImg")
    void updateClassBanner(@Header("token") String token,
                           @Query("ClassId") long classId,
                           @Query("type") String type,
                           @Part("photo") TypedFile photo,
                           Callback<ResponseData<BannerModel>> callback);

    //移除助教权限
    @FormUrlEncoded
    @POST("/HerbAssistant/RemoveAssistantRoleByClass")
    void removeTutorRole(@Header("token")String token,
                         @Field("assistantId")long tutorId,
                         @Field("classId")long classId,
                         @Field("messageId")long messageId,
                         Callback<ResponseData> callback);

    //获取班级动态
    @GET("/BasicData/GetClassDynamic")
    void getClassDynamic(@Header("token")String token,
                         @Query("classid")long classId,
                         @Query("pageIndex")int pageIndex,
                         RequestCallback<ResponseData<List<DynamicInfoModel>>> callback);
}
