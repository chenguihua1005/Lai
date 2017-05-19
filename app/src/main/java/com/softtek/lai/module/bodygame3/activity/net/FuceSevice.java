package com.softtek.lai.module.bodygame3.activity.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.activity.model.AuditListModel;
import com.softtek.lai.module.bodygame3.activity.model.FcAuditPostModel;
import com.softtek.lai.module.bodygame3.activity.model.FcStDataModel;
import com.softtek.lai.module.bodygame3.activity.model.FuceImgModel;
import com.softtek.lai.module.bodygame3.activity.model.InitAuditPModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.bodygame3.activity.model.MeasureListModel;
import com.softtek.lai.module.bodygame3.activity.model.MeasureStModel;
import com.softtek.lai.module.bodygame3.activity.model.MemberListModel;
import com.softtek.lai.module.bodygame3.head.model.MeasuredDetailsModel;
import com.softtek.lai.module.laicheng.model.BleMainData;

import java.io.File;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
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
            @Header("token") String token,
            @Body MultipartTypedOutput multipartTypedOutput,
            Callback<ResponseData> callback
    );

    //获取复测详情
//    @Ethan.Tung   accountId classId
    @GET("/v1/MeasuredRecordLog/GetMeasuredDetails")
    void doGetMeasuredDetails(
            @Header("token") String token,
            @Query("acmId") String acmId,
//            @Query("accountId") long accountId,
//            @Query("classId") String classId,
//            @Query("dateType") String dateType,
            Callback<ResponseData<MeasuredDetailsModel>> callback
    );

    ///v1/MeasuredRecordLog/GetMeasuredDataByAcmid
    //获取个人复测信息
    @GET("/v1/MeasuredRecordLog/GetMeasuredDataByAcmid")
    void doGetMeasuredData(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            @Query("typeDate") String typeDate,
            Callback<ResponseData<MeasureStModel>> callback
    );

    //学员基础信息
    ///v1/MeasuredRecordLog/GetPreMeasureData
    @GET("/v1/MeasuredRecordLog/GetPreMeasureData")
    void doGetPreMeasureData(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            @Query("typeDate") String typeDate,
            @Query("type") String type,
            Callback<ResponseData<MeasuredDetailsModel>> callback
//            Callback<ResponseData<FcStDataModel>> callback   // old
    );

    //学员基础信息   2
    @GET("/v1/MeasuredRecordLog/GetPreMeasureData")
    void getPreMeasureData(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            @Query("typeDate") String typeDate,
            @Query("type") String type,
            Callback<ResponseData<MeasuredDetailsModel>> callback
    );

    //    教练或助教为学员复测审核（学员未录入） /v1/MeasuredRecordLog/MeasureForMembers
    @POST("/v1/MeasuredRecordLog/MeasureForMembers")
    void postMeasureForMembers(
            @Header("token") String token,
            @Query("type") int type,
            @Body FcAuditPostModel model,
            Callback<ResponseData> callback
    );

    //获取初始数据录入 数据   学员
    @GET("/v1/MeasuredRecordLog/GetPreMeasureDataVs1")
    void getPreMeasureDataVs1(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            @Query("typeDate") String typeDate,
            @Query("type") String type,
            Callback<ResponseData<MeasuredDetailsModel>> callback
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
//    @POST("/v1/MeasuredRecordLog/ReviewInitData")
//    void doReviewInitData(
//            @Header("token") String token,
//            @Body InitAuditPModel initAuditPModel,
//            Callback<ResponseData> callback
//    );

    //初始数据审核提交
    @POST("/v1/MeasuredRecordLog/ReviewInitData")
    void doReviewInitData(
            @Header("token") String token,
            @Body FcAuditPostModel initAuditPModel,
            Callback<ResponseData> callback
    );

    //复测数据提交审核
    @POST("/v1/MeasuredRecordLog/ReviewMeasuredRecord")
    void doReviewMeasuredRecord(
            @Header("token") String token,
            @Body FcAuditPostModel fcAuditPostModel,
            Callback<ResponseData> callback
    );

    //    获取未复测学员列表
    @GET("/v1/MeasuredRecordLog/GetClassInitDataList")
    void getInitMeasureList(
            @Header("classid") String CId,
            @Header("token") String token,
            @Query("accountId") Long accountId,
            @Query("classId") String classId,
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            Callback<ResponseData<List<MeasureListModel>>> callback
    );

    //上传照片
    @Multipart
    @POST("/V1/FileUpload/PostFile")
    void uploadphoto(
            @Header("token") String token,
            @Field("image") File img,
            Callback<ResponseData<FuceImgModel>> callback
    );

    //莱秤复测数据提交 (在数据确认之前调用)
    @POST("/v1/DataSync/LBDataSubmit")
    void LBDataSubmit(
            @Header("token") String token,
            @Query("recordId") String recordId,
            @Query("type") int type,
            @Query("classId") String classId,
            Callback<ResponseData<BleMainData>> callback
    );


}
