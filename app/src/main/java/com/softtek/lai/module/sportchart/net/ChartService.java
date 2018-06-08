package com.softtek.lai.module.sportchart.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.message2.model.PhotosModel;
import com.softtek.lai.module.sportchart.model.StepCountModel;
import com.softtek.lai.utils.RequestCallback;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by lareina.qiao on 10/19/2016.
 */
public interface ChartService {
    /*运动个人主页接口*/
    @GET("/StepCount/NewGetStepCount")
    void doGetStepCount(
            @Header("token")String token,
            @Query("accountid") String accountid,
            @Query("start")String start,
            @Query("end")String end,
            RequestCallback<ResponseData<StepCountModel>>callback
    );
    /*关注用户*/
    @POST("/HealthyCircle/FocusAccount")
    void doFocusAccount(
            @Header("token")String token,
            @Query("accountid") String accountid,
            @Query("focusaccid") String focusaccid,
            RequestCallback<ResponseData>callback
    );
    /*取消关注用户*/
    @POST("/HealthyCircle/CancleFocusAccount")
    void doCancleFocusAccount(
            @Header("token")String token,
            @Query("accountid") String accountid,
            @Query("focusaccid") String focusaccid,
            RequestCallback<ResponseData>callback
    );
    //上传banner图片
    //上传图片
    @POST("/HerbrClass/UpClassImg")
    @Multipart
    void doUploadPhoto(
            @Header("token") String token,
            @Query("ClassId") String ClassId,
            @Query("type")String type,
            @Part("photo") TypedFile photo,
            RequestCallback<ResponseData<com.softtek.lai.module.sportchart.model.PhotModel>> callback
    );

    //上传图片
    @POST("/FileUpload/PostFile")
    @Multipart
    void upimg(
            @Header("token") String token,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<PhotosModel>> callback);
}
