/*
 * Copyright (C) 2010-2016 Softtek Information Systems (Wuxi) Co.Ltd.
 * Date:2016-03-31
 */

package com.softtek.lai.module.retest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;
import com.softtek.lai.module.retest.model.*;
import retrofit.Callback;
import retrofit.http.*;
import retrofit.mime.TypedFile;

import java.util.List;

/**
 * Created by lareina.qiao on 3/21/2016.
 */
public interface RestService {
    //展示全部班级列表接口
    @GET("/MeasuredRecordLog/ShowAllClassListBySP")
    void doGetRetestclass(@Header("token") String token,
                          @Query("id") long id,
                          Callback<ResponseData<List<BanjiModel>>> callback);

    //关键字查询结果接口
    @GET("/MeasuredRecordLog/SearchMeasuredInfoByKeyword")
    void doGetqueryResult(
            @Header("token") String token,
            @Query("str") String str,
            Callback<ResponseData<List<StudentModel>>> callback
    );

    //获取班级学员
    @GET("/MeasuredRecordLog/SearchMeasuredInfoByClassId")
    void doGetBanjiStudent(
            @Header("token") String token,
            @Query("classId") long classId,
            Callback<ResponseData<List<BanjiStudentModel>>> callback
    );

    //复测审核获取数据
    @GET("/MeasuredRecordLog/GetMeasuredRecord")
    void doGetAudit(
            @Header("token") String token,
            @Query("accountId") long accountId,
            @Query("classId") long classId,
            @Query("typeDate") String typeDate,
            Callback<ResponseData<List<RetestAuditModel>>> callback
    );

    //复测审核提交数据接口
    @PUT("/MeasuredRecordLog/ReviewMeasuredRecord")
    void doPostAudit(
            @Header("token") String token,
            @Query("loginId") String loginId,
            @Query("accountId") String accountId,
            @Query("typeDate") String typeDate,
            @Body RetestAuditModel retestAuditModel,
            Callback<ResponseData<List<RetestAuditModel>>> callback
    );

    //复测录入提交
    @POST("/MeasuredRecordLog/SaveMeasuredRecord")
    void doPostWrite(
            @Header("token") String token,
            @Query("accountId") long accountId,
            @Query("loginId") long loginId,
            @Body RetestWriteModel retestWriteModel,
            Callback<ResponseData<RetestWriteModel>> callback
    );

    //上传图片
    @POST("/MeasuredRecordLog/AddMeasuredPhoto")
    @Multipart
    void goGetPicture(
            @Header("token") String token,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<PhotModel>> callback
    );

    //莱秤接口
    @GET("http://api.yunyingyang.com/herbalife/getmeasures")
    void doGetMeasure(
            @Query("accesstoken") String accesstoken,
            @Query("phone") String phone,
            Callback<ResponseData<MeasureModel>> callback

    );

}
