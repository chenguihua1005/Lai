package com.softtek.lai.module.grade.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.grade.model.Grade;
import com.softtek.lai.module.grade.model.SRInfo;
import com.softtek.lai.module.grade.model.Student;
import com.squareup.okhttp.Call;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
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
    //发布动态
    @FormUrlEncoded
    @POST("/HerbrClass/CreatDynamic")
    void senDynamic(@Header("token")String token,
                    @Field("ClassId")long classId,
                    @Field("DyInfoTitle")String dyTitle,
                    @Field("DyContent")String dyContent,
                    @Field("DyType")int dyType,
                    @Field("AccountId")long accountId,
                    Callback<ResponseData> callback);

    //获取学员列表
    @GET("/HerbrClass/SearchClassMember")
    void getStudentsList(@Header("token")String token,
                         @Query("classId")String classId,
                         @Query("ordertype")String orderType,
                         Callback<ResponseData<List<Student>>> callback);
    //获取助教列表
    @GET("/HerbrClass/GetSRList")
    void getTutorList(@Header("token")String token,
                      @Query("classid")long classId,
                      Callback<ResponseData<List<SRInfo>>> callback);
}
