package com.softtek.lai.module.bodygame3.activity.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.model.InitComitModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.bodygame3.activity.model.MeasureStModel;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.retest.model.MeasureModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;

/**
 * Created by lareina.qiao on 2016/11/22.
 */

public interface FuceSevice {
    //    获取初始数据接口
    @GET("/v1/MeasuredRecordLog/GetPreMeasureData")
    void dogetInitData(
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            Callback<ResponseData<InitDataModel>> callback
    );

    //    提交初始数据接口
    @POST("/v1/MeasuredRecordLog/PostInitData")
    void doPostInitData(
            @Header("token") String token,
            @Body MultipartTypedOutput multipartTypedOutput,
            Callback<ResponseData> callback
    );

    //    获取复测审核列表
    @GET("/v1/MeasuredRecordLog/GetMeasureReviewedList")
    void dogetAuditList(
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            @Query("typeDate") String typeDate,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            Callback<ResponseData<List<AuditListModel>>> callback
    );
    //    获取初始数据审核列表
    @GET("/v1/MeasuredRecordLog/GetClassInitDataList")
    void dogetInitAuditList(
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            Callback<ResponseData<List<AuditListModel>>> callback
    );
    //复测录入
    @POST("/v1/MeasuredRecordLog/PostMeasuredData")
    void doPostMeasuredData(
            @Header("token")String token,
            @Body MultipartTypedOutput multipartTypedOutput,
            Callback<ResponseData>callback
            );
    //获取复测详情
    @GET("/v1/MeasuredRecordLog/GetMeasuredDetails")
    void doGetMeasuredDetails(
            @Header("token")String token,
            @Query("acmId")String acmId,
            Callback<ResponseData<MeasuredDetailsModel>>callback
    );
    ///v1/MeasuredRecordLog/GetMeasuredDataByAcmid
    //获取个人复测信息
    @GET("/v1/MeasuredRecordLog/GetMeasuredDataByAcmid")
    void doGetMeasuredData(
            @Header("token")String token,
            @Query("accountId")Long accountId,
            @Query("classId")String classId,
            @Query("typeDate")String typeDate,
            Callback<ResponseData<MeasureStModel>>callback
            );






}
