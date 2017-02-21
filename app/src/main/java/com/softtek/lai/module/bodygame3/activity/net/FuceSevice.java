package com.softtek.lai.module.bodygame3.activity.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.model.FcAuditPostModel;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.model.InitAuditPModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.bodygame3.activity.model.MeasureStModel;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
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
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            Callback<ResponseData<InitDataModel>> callback
    );


    //    获取复测审核列表
    @GET("/v1/MeasuredRecordLog/GetMeasureReviewedList")
    void dogetAuditList(
            @Header("classid") String CId,
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
            @Header("classid") String CId,
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
            @Header("classid") String classid,
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
            @Header("classid") String CId,
            @Header("token")String token,
            @Query("accountId")Long accountId,
            @Query("classId")String classId,
            @Query("typeDate")String typeDate,
            Callback<ResponseData<MeasureStModel>>callback
            );
    //学员基础信息
    ///v1/MeasuredRecordLog/GetPreMeasureData
    @GET("/v1/MeasuredRecordLog/GetPreMeasureData")
    void doGetPreMeasureData(
            @Header("classid") String CId,
            @Header("token")String token,
            @Query("accountId")Long accountId,
            @Query("classId")String classId,
            @Query("typeDate")String typeDate,
            @Query("type")String type,
            Callback<ResponseData<FcStDataModel>>callback
            );
    //    提交初始数据接口
    @POST("/v1/MeasuredRecordLog/PostInitData")
    void doPostInitData(
            @Header("classid") String classid,
            @Header("token") String token,
            @Body MultipartTypedOutput multipartTypedOutput,
            Callback<ResponseData> callback
    );

    //初始数据审核提交
    @POST("/v1/MeasuredRecordLog/ReviewInitData")
    void doReviewInitData(
            @Header("token") String token,
            @Body InitAuditPModel initAuditPModel,
            Callback<ResponseData>callback
            );
    //复测数据提交审核
    @POST("/v1/MeasuredRecordLog/ReviewMeasuredRecord")
    void doReviewMeasuredRecord(
            @Header("token") String token,
            @Body FcAuditPostModel fcAuditPostModel,
            Callback<ResponseData>callback
            );

}
