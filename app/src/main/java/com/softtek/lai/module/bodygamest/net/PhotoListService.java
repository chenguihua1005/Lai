package com.softtek.lai.module.bodygamest.net;

import com.softtek.lai.common.ResponseData;
import com.softtek.lai.module.bodygamest.model.DownPhotoModel;
import com.softtek.lai.module.bodygamest.model.UploadPhotModel;
import com.softtek.lai.module.newmemberentry.view.model.PhotModel;


import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by lareina.qiao on 3/31/2016.
 */
public interface PhotoListService {
    //获取图片列表接口
    @GET("/UploadPhotos/GetUploadPhoto")
    void doGetDownPhoto(
            @Header("token")String token,
            @Query("AccountId")String AccountId,
            @Query("pageIndex")int pageIndex,
            Callback<ResponseData<DownPhotoModel>>callback
    );
    //上传图片
    @POST("/UploadPhotos/PostUploadPhotos")
    @Multipart
    void doUploadPhoto(
            @Header("token") String token,
            @Query("AccountId")String AccountId,
            @Part("photo") TypedFile photo,
            Callback<ResponseData<UploadPhotModel>> callback
    );

    //获取图片列表接口
    @GET("/UploadPhotos/GetUploadPhoto")
    void getUploadPhoto(
            @Header("token")String token,
            @Query("AccountId")String AccountId,
            @Query("pageIndex")String pageIndex,
            Callback<ResponseData<DownPhotoModel>> callback
    );

    //获取图片列表接口
    @FormUrlEncoded
    @POST("/HerbUser/GetUserPhotos")
    void getUserPhotos(
            @Header("token")String token,
            @Field("PhotoName")String photoName,
            Callback<ResponseData> callback
    );
}
