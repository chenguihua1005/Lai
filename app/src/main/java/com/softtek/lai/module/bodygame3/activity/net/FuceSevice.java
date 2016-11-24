package com.softtek.lai.module.bodygame3.activity.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygame3.activity.model.InitComitModel;
import com.softtek.lai.module.bodygame3.activity.model.InitDataModel;
import com.softtek.lai.module.bodygame3.head.model.ClassinfoModel;

import java.io.File;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.MultipartTypedOutput;
import retrofit.mime.TypedFile;

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
//    @Multipart
    void doPostInitData(
            @Header("token") String token,
//            @Part("accountId") Long accountId,
//            @Part("classId") String classId,
//            @Part("pysical") double pysical,
//            @Part("ChuWeight") double ChuWeight,
//            @Part("Fat") double fat,
//            @Part("Circum") double circum,
//            @Part("Waistline") double waistline,
//            @Part("Hipline") double hipline,
//            @Part("UpArmGirth") double upArmGirth,
//            @Part("UpLegGirth") double upLegGirth,
//            @Part("DoLegGirth") double doLegGirth,
//            @Part("image") TypedFile image,
//            @Body InitComitModel initComitModel,
            @Body MultipartTypedOutput multipartTypedOutput,
            Callback<ResponseData> callback
    );


}
